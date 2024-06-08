package com.example.testrestservice.service.impl;

import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.entity.Room;
import com.example.testrestservice.entity.TypeComfort;
import com.example.testrestservice.entity.TypeRoom;
import com.example.testrestservice.exception.ValidationException;
import com.example.testrestservice.mapper.MapStructMapper;
import com.example.testrestservice.repository.GuestRepository;
import com.example.testrestservice.repository.RoomRepository;
import com.example.testrestservice.service.RoomService;
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
public class RoomServiceImpl implements RoomService {

    private final Supplier<ValidationException> supplierUpdatedRoom = () -> new ValidationException("updatedRoom is incorrect!");

    private static final int MIN_ROOM_NUMBER = 1;
    private static final int MAX_ROOM_NUMBER = 100;
    private static final int MIN_ROOM_FLOOR = 1;
    private static final int MAX_ROOM_FLOOR = 10;
    private static final int MIN_ROOM_SEATS_NUMBER = 1;

    private final RoomRepository roomRepository;
    private final MapStructMapper mapper;
    private final GuestRepository guestRepository;


    @Override
    public List<RoomDto> findAllByFilter(String typeRoom, String typeComfort, Boolean isAvailable) {
        if(typeRoom != null) {
            return findAllByTypeRoom(typeRoom);
        }
        else if(typeComfort != null) {
            return findAllByTypeComfort(typeComfort);
        }
        else if(isAvailable != null) {
            return findAllRoomsByAvailableSeat();
        }
        else {
            return findAll();
        }
    }

    @Override
    public RoomDto saveRoom(RoomDto roomDto) {
        Utils.checkNotNull(roomDto, "roomDto");

        validateIntValByMinMax(
                roomDto.getFloor(), MIN_ROOM_FLOOR, MAX_ROOM_FLOOR, "roomDto.getFloor()"
        );
        validateIntValByMinMax(
                roomDto.getRoomNumber(), MIN_ROOM_NUMBER, MAX_ROOM_NUMBER, "roomDto.getRoomNumber()"
        );
        validateUniqueNumberRoom(roomDto.getRoomNumber(), "roomDto.getRoomNumber()");

        TypeRoom typeRoom = getTypeRoom(roomDto.getTypeRoom(), "roomDto.getTypeRoom()");
        TypeComfort typeComfort = getTypeComfort(roomDto.getTypeComfort(), "roomDto.getTypeComfort()");
        validateRoomSeatsNumber(roomDto.getSeatsNumber(), "roomDto.getSeatsNumber()");

        roomDto.setTypeRoom(typeRoom);
        roomDto.setTypeComfort(typeComfort);
        LocalDate dateCreate = LocalDate.now();
        roomDto.setDateCreation(dateCreate);
        roomDto.setDateChange(dateCreate);

        return mapper.roomToRoomDto(roomRepository.save(mapper.RoomDtoToRoom(roomDto)));
    }

    @Override
    public RoomDto updateRoom(RoomDto roomDto, Long roomId) {
        Room updatedRoom = roomRepository.findById(roomId).orElseThrow(supplierUpdatedRoom);
        Utils.checkNotNull(updatedRoom, "updatedRoom");

        TypeRoom updatedTypeRoom = roomDto.getTypeRoom();
        TypeComfort updatedTypeComfort = roomDto.getTypeComfort();
        Integer updatedSeatsNumber = roomDto.getSeatsNumber();

        validateRoomSeatsNumber(updatedSeatsNumber, "updatedSeatsNumber");
        updatedTypeRoom = getTypeRoom(updatedTypeRoom, "updatedTypeRoom");
        updatedTypeComfort = getTypeComfort(updatedTypeComfort, "updatedTypeComfort");

        validateUpdatedRoomData(
                updatedRoom,
                updatedTypeRoom,
                "updatedTypeRoom",
                updatedTypeComfort,
                "updatedTypeComfort",
                updatedSeatsNumber,
                "updatedSeatsNumber");

        updatedRoom.setTypeRoom(updatedTypeRoom);
        updatedRoom.setTypeComfort(updatedTypeComfort);
        updatedRoom.setSeatsNumber(updatedSeatsNumber);
        LocalDate dateChange = LocalDate.now();
        updatedRoom.setDateChange(dateChange);

        return mapper.roomToRoomDto(roomRepository.save(updatedRoom));
    }

    @Override
    public void deleteRoom(Long roomId) {
        validateRoomIsExistAndNoGuests(roomId, "roomId");
        roomRepository.deleteById(roomId);
    }

    private List<RoomDto> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(mapper::roomToRoomDto)
                .collect(Collectors.toList());
    }

    private List<RoomDto> findAllByTypeRoom(String typeRoom) {
        return roomRepository.findAll()
                .stream()
                .filter(room -> TypeRoom.fromValue(typeRoom, "typeRoom").getValue().equals(room.getTypeRoom().getValue()))
                .map(mapper::roomToRoomDto)
                .collect(Collectors.toList());
    }

    private List<RoomDto> findAllRoomsByAvailableSeat() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> (room.getSeatsNumber() - guestRepository.countGuestByRoom_Id(room.getId())) > 0)
                .map(mapper::roomToRoomDto)
                .collect(Collectors.toList());
    }

    private List<RoomDto> findAllByTypeComfort(String typeComfort) {
        return roomRepository.findAll()
                .stream()
                .filter(room -> TypeComfort.fromValue(typeComfort, "typeComfort").getValue().equals(room.getTypeComfort().getValue()))
                .map(mapper::roomToRoomDto)
                .collect(Collectors.toList());
    }

    private void validateRoomIsExistAndNoGuests(Long roomId, String nameId){
        if(roomRepository.findById(roomId).isEmpty()) {
            String errorMsg = String.format("The room with %s = %d doesn't exist!", nameId, roomId);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
        else if(guestRepository.countGuestByRoom_Id(roomId) > 0) {
            String errorMsg = String.format("Guests live in the room with %s = %d", nameId, roomId);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private void validateUpdatedRoomData(
            Room updatedRoom,
            TypeRoom newTypeRoom,
            String nameNewTypeRoom,
            TypeComfort newTypeComfort,
            String nameNewTypeComfort,
            Integer newSeatsNumber,
            String nameNewSeatsNumber) {
        if (
                (updatedRoom.getTypeRoom() == newTypeRoom) &&
                        (updatedRoom.getTypeComfort() == newTypeComfort) &&
                        (Objects.equals(updatedRoom.getSeatsNumber(), newSeatsNumber))
        ) {
            String errorMsg = String.format(
                    "The update data (%s, %s, %s) has not changed!",
                    nameNewTypeRoom,
                    nameNewTypeComfort,
                    nameNewSeatsNumber
            );
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private TypeRoom getTypeRoom(TypeRoom typeRoom, String nameObject) {
        Utils.checkNotNull(typeRoom, nameObject);
        return TypeRoom.fromValue(typeRoom.getValue(), nameObject);
    }

    private TypeComfort getTypeComfort(TypeComfort typeComfort, String nameObject) {
        Utils.checkNotNull(typeComfort, nameObject);
        return TypeComfort.fromValue(typeComfort.getValue(), nameObject);
    }

    private void validateRoomSeatsNumber(Integer seatsNumber, String nameObject) {
        Utils.checkNotNull(seatsNumber, nameObject);
        if(seatsNumber < MIN_ROOM_SEATS_NUMBER) {
            String errorMsg = String.format("%s must be at least %d!", nameObject, MIN_ROOM_NUMBER);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private void validateIntValByMinMax(Integer value, Integer minValue, Integer maxValue, String nameObject) {
        Utils.checkNotNull(value, nameObject);
        if (value < minValue || value > maxValue) {
            String errorMsg = String.format("%s must be at least %d and no more than %d!", nameObject, MIN_ROOM_NUMBER, MAX_ROOM_NUMBER);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

    private void validateUniqueNumberRoom(Integer value, String nameObject) {
        if (roomRepository.findByRoomNumber(value).isPresent()){
            String errorMsg = String.format("%s is not unique!", nameObject);
            log.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
    }

}
