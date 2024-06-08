package com.example.testrestservice.service.impl;

import com.example.testrestservice.dto.GuestDto;
import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.entity.*;
import com.example.testrestservice.exception.ValidationException;
import com.example.testrestservice.mapper.MapStructMapper;
import com.example.testrestservice.repository.GuestRepository;
import com.example.testrestservice.repository.RoomRepository;
import com.example.testrestservice.service.GuestService;
import com.example.testrestservice.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final Supplier<ValidationException> supplierFoundRoomDto = () -> new ValidationException("foundRoomDto is incorrect!");
    private final Supplier<ValidationException> supplierUpdatedGuest = () -> new ValidationException("updatedGuest is incorrect!");
    private final Supplier<ValidationException> supplierUpdatedRoom = () -> new ValidationException("updatedRoom is incorrect!");

    private final GuestRepository guestRepository;
    private final MapStructMapper mapper;
    private final RoomRepository roomRepository;


    @Override
    public List<GuestDto> findAllByFilter(String gender, Integer numberRoom, String typeComfort) {
        if(gender != null) {
            return findAllByGender(gender);
        }
        else if(numberRoom != null) {
            return findAllByRoom(numberRoom);
        }
        else if(typeComfort != null) {
            return findAllByRoomTypeComfort(typeComfort);
        }
        else {
            return findAll();
        }
    }

    @Override
    public GuestDto saveGuest(GuestDto guestDto) {
        RoomDto foundRoomDto = roomRepository.findById(guestDto.getRoomId()).map(mapper::roomToRoomDto).orElseThrow(supplierFoundRoomDto);
        Utils.checkNotNull(foundRoomDto, "foundRoomDto");

        validateLetterStringValue(guestDto.getFirstName().trim(), "guestDto.getFirstName()");
        validateLetterStringValue(guestDto.getLastName().trim(), "guestDto.getLastName()");
        validateLetterStringValue(guestDto.getPatronymic().trim(), "guestDto.getPatronymic()");
        Gender genderGuestDto = getGender(guestDto.getGender(), "guestDto.getGender()");

        validateGender(
                foundRoomDto.getTypeRoom(), genderGuestDto, "foundRoomDto.getTypeRoom()", "genderGuestDto"
        );
        validateAvailableSeatInRoom(foundRoomDto, "foundRoomDto");

        guestDto.setGender(genderGuestDto);

        LocalDate dateCreation = LocalDate.now();
        guestDto.setDateCreation(dateCreation);
        guestDto.setDateChange(dateCreation);

        return mapper.guestToGuestDto(guestRepository.save(mapper.guestDtoToGuest(guestDto)));
    }

    @Override
    public GuestDto updateGuest(GuestDto guestDto, Long guestId) {
        Guest updatedGuest = guestRepository.findById(guestId).orElseThrow(supplierUpdatedGuest);
        Utils.checkNotNull(updatedGuest, "updatedGuest");

        RoomDto updatedRoomDto = roomRepository.findById(guestDto.getRoomId()).map(mapper::roomToRoomDto).orElseThrow(supplierUpdatedRoom);
        Utils.checkNotNull(updatedRoomDto, "updatedRoomDto");

        LocalDate dateChange = LocalDate.now();
        updatedGuest.setDateChange(dateChange);
        updatedGuest.setRoom(mapper.RoomDtoToRoom(updatedRoomDto));

        return mapper.guestToGuestDto(guestRepository.save(updatedGuest));
    }

    @Override
    public void deleteGuest(Long guestId) {
        validateGuestIsExist(guestId, "guestId");
        guestRepository.deleteById(guestId);
    }

    private List<GuestDto> findAll() {
        return guestRepository.findAll()
                .stream()
                .map(mapper::guestToGuestDto)
                .collect(Collectors.toList());
    }

    private List<GuestDto> findAllByGender(String gender) {
        return guestRepository.findAll()
                .stream()
                .filter(guest -> Gender.fromValue(gender, "gender").getValue().equals(guest.getGender().getValue()))
                .map(mapper::guestToGuestDto)
                .collect(Collectors.toList());
    }

    private List<GuestDto> findAllByRoom(Integer numberRoom) {
        return guestRepository.findAll()
                .stream()
                .filter(guest -> guest.getRoom().getRoomNumber().equals(numberRoom))
                .map(mapper::guestToGuestDto)
                .collect(Collectors.toList());
    }

    private List<GuestDto> findAllByRoomTypeComfort(String typeComfort) {
        return guestRepository.findAll()
                .stream()
                .filter(guest -> TypeComfort.fromValue(typeComfort, "typeComfort").getValue().equals(guest.getRoom().getTypeComfort().getValue()))
                .map(mapper::guestToGuestDto)
                .collect(Collectors.toList());
    }

    private void validateAvailableSeatInRoom(RoomDto foundRoomDto, String nameRoomDto) {
        Integer countGuestsInRoom = guestRepository.countGuestByRoom_Id(foundRoomDto.getId());
        Integer countAllSeatsInRoom = foundRoomDto.getSeatsNumber();
        if(Objects.equals(countGuestsInRoom, countAllSeatsInRoom)) {
            String errorMsg = String.format("There is no available seat in %s!", nameRoomDto);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private void validateLetterStringValue(String value, String nameObject) {
        Utils.checkNotEmpty(value, nameObject);
        if (!value.matches("^[а-яА-Я]+$")) {
            String errorMsg = String.format("%s must contain only Cyrillic letters!", nameObject);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private void validateGender(TypeRoom typeRoomDto, Gender genderGuestDto, String nameTypeRoom, String nameGender) {
        String typeRoom = typeRoomDto.getValue();
        String genderGuest = genderGuestDto.getValue();
        if(!typeRoom.equals(genderGuest)) {
            String errorMsg = String.format("%s isn't equal to %s!", nameTypeRoom, nameGender);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private void validateGuestIsExist(Long guestId, String nameId) {
        if (guestRepository.findById(guestId).isEmpty()) {
            String errorMsg = String.format("The guest with %s = %d doesn't exist!", nameId, guestId);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private Gender getGender(Gender gender, String nameObject) {
        Utils.checkNotNull(gender, nameObject);
        return Gender.fromValue(gender.getValue(), nameObject);
    }

}
