package com.example.testrestservice.service;

import com.example.testrestservice.dto.GuestDto;
import com.example.testrestservice.entity.*;
import com.example.testrestservice.exception.ValidationException;
import com.example.testrestservice.mapper.MapStructMapperImpl;
import com.example.testrestservice.repository.GuestRepository;
import com.example.testrestservice.repository.RoomRepository;
import com.example.testrestservice.service.impl.GuestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {

    @Spy
    MapStructMapperImpl mapper;

    @InjectMocks
    GuestServiceImpl guestServiceImpl;

    @Mock
    GuestRepository guestRepository;

    @Mock
    RoomRepository roomRepository;

    private Room room1;
    private Room room2;
    private Guest guest1;
    private Guest guest2;


    @BeforeEach
    void setup() {
        room1 = new Room(1L, 1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 1, LocalDate.now(), LocalDate.now());
        room2 = new Room(3L, 3, 9, TypeRoom.FEMALE, TypeComfort.LUX, 2, LocalDate.now(), LocalDate.now());
        guest1 = new Guest(1L, room1, "Иван", "Иванов", "Иванович", Gender.MALE, LocalDate.now(), LocalDate.now());
        guest2 = new Guest(1L, room2, "Анна", "Иванова", "Ивановна", Gender.FEMALE, LocalDate.now(), LocalDate.now());
    }

    private List<Guest> getGuests() {
        return List.of(guest1, guest2);
    }

    @Test
    public void shouldReturnAllGuestsWhenCallFindAll() {
        List<Guest> listGuests = getGuests();

        Mockito.when(guestRepository.findAll()).thenReturn(listGuests);

        String gender = null;
        Integer numberRoom = null;
        String typeComfort = null;

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(mapper, Mockito.times(listGuests.size())).guestToGuestDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(listGuests.size(), result.size());
        Assertions.assertEquals(listGuests.get(0).getId(), result.get(0).getId());
        Mockito.verify(guestRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAll() {
        Mockito.when(guestRepository.findAll()).thenReturn(List.of());

        String gender = null;
        Integer numberRoom = null;
        String typeComfort = null;

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(guestRepository).findAll();

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnAllGuestsWhenCallFindAllByGender() {
        List<Guest> listGuests = getGuests();

        Mockito.when(guestRepository.findAll()).thenReturn(listGuests);

        String gender = "male";
        Integer numberRoom = null;
        String typeComfort = null;

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(mapper, Mockito.times(1)).guestToGuestDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(listGuests.get(0).getId(), result.get(0).getId());
        Mockito.verify(guestRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAllByGender() {
        Mockito.when(guestRepository.findAll()).thenReturn(List.of());

        String gender = "male";
        Integer numberRoom = null;
        String typeComfort = null;

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(guestRepository).findAll();

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnAllGuestsWhenCallFindAllByRoom() {
        List<Guest> listGuests = getGuests();

        Mockito.when(guestRepository.findAll()).thenReturn(listGuests);

        String gender = null;
        Integer numberRoom = room1.getRoomNumber();
        String typeComfort = null;

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(mapper, Mockito.times(1)).guestToGuestDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(listGuests.get(0).getId(), result.get(0).getId());
        Mockito.verify(guestRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAllByRoom() {
        Mockito.when(guestRepository.findAll()).thenReturn(List.of());

        String gender = null;
        Integer numberRoom = room1.getRoomNumber();
        String typeComfort = null;

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(guestRepository).findAll();

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnAllGuestsWhenCallFindAllByRoomTypeComfort() {
        List<Guest> listGuests = getGuests();

        Mockito.when(guestRepository.findAll()).thenReturn(listGuests);

        String gender = null;
        Integer numberRoom = null;
        String typeComfort = room1.getTypeComfort().getValue();

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(mapper, Mockito.times(1)).guestToGuestDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(listGuests.get(0).getId(), result.get(0).getId());
        Mockito.verify(guestRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAllByRoomTypeComfort() {
        Mockito.when(guestRepository.findAll()).thenReturn(List.of());

        String gender = null;
        Integer numberRoom = null;
        String typeComfort = room1.getTypeComfort().getValue();

        List<GuestDto> result = guestServiceImpl.findAllByFilter(gender, numberRoom, typeComfort);
        Mockito.verify(guestRepository).findAll();

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void whenSaveGuestShouldReturnCorrectGuestDto() {
        GuestDto guestDtoToSave = mapper.guestToGuestDto(guest1);
        Mockito.verify(mapper).guestToGuestDto(guest1);

        Mockito.when(guestRepository.save(ArgumentMatchers.any())).thenReturn(guest1);
        Mockito.when(roomRepository.findById(guest1.getRoom().getId())).thenReturn(Optional.of(room1));

        GuestDto guestDtoSaved = guestServiceImpl.saveGuest(guestDtoToSave);
        Mockito.verify(guestRepository).save(ArgumentMatchers.any());
        Mockito.verify(roomRepository).findById(guest1.getRoom().getId());

        Assertions.assertEquals(guest1.getId(), guestDtoSaved.getId());
        Assertions.assertEquals(guest1.getRoom().getId(), guestDtoSaved.getRoomId());
        Assertions.assertEquals(guest1.getFirstName(), guestDtoSaved.getFirstName());
        Assertions.assertEquals(guest1.getLastName(), guestDtoSaved.getLastName());
        Assertions.assertEquals(guest1.getPatronymic(), guestDtoSaved.getPatronymic());
        Assertions.assertEquals(guest1.getGender(), guestDtoSaved.getGender());
        Assertions.assertEquals(guest1.getDateChange(), guestDtoSaved.getDateChange());
        Assertions.assertEquals(guest1.getDateCreation(), guestDtoSaved.getDateCreation());
    }

    @Test
    public void whenSaveEmptyShouldReturnValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(new GuestDto()));
    }

    @Test
    public void whenSaveGuestWithNullAndIncorrectFirstNameShouldReturnException() {
        Guest guestToSave = new Guest();

        guestToSave.setId(guest1.getId());
        guestToSave.setRoom(room1);
        guestToSave.setGender(guest1.getGender());
        guestToSave.setLastName(guest1.getLastName());
        guestToSave.setPatronymic(guest1.getPatronymic());
        guestToSave.setDateChange(guest1.getDateChange());
        guestToSave.setDateCreation(guest1.getDateCreation());

        GuestDto guestDtoToSave = mapper.guestToGuestDto(guestToSave);
        Mockito.verify(mapper).guestToGuestDto(guestToSave);

        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));

        String incorrectFirstName = "Иван1337";
        guestToSave.setFirstName(incorrectFirstName);
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));
    }

    @Test
    public void whenSaveGuestWithNullAndIncorrectLastNameShouldReturnException() {
        Guest guestToSave = new Guest();

        guestToSave.setId(guest1.getId());
        guestToSave.setRoom(room1);
        guestToSave.setGender(guest1.getGender());
        guestToSave.setFirstName(guest1.getFirstName());
        guestToSave.setPatronymic(guest1.getPatronymic());
        guestToSave.setDateChange(guest1.getDateChange());
        guestToSave.setDateCreation(guest1.getDateCreation());

        GuestDto guestDtoToSave = mapper.guestToGuestDto(guestToSave);
        Mockito.verify(mapper).guestToGuestDto(guestToSave);

        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));

        String incorrectLastName = "Иванов1337";
        guestToSave.setLastName(incorrectLastName);
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));
    }

    @Test
    public void whenSaveGuestWithNullAndIncorrectPatronymicShouldReturnException() {
        Guest guestToSave = new Guest();

        guestToSave.setId(guest1.getId());
        guestToSave.setRoom(room1);
        guestToSave.setGender(guest1.getGender());
        guestToSave.setFirstName(guest1.getFirstName());
        guestToSave.setLastName(guest1.getLastName());
        guestToSave.setDateChange(guest1.getDateChange());
        guestToSave.setDateCreation(guest1.getDateCreation());

        GuestDto guestDtoToSave = mapper.guestToGuestDto(guestToSave);
        Mockito.verify(mapper).guestToGuestDto(guestToSave);

        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));

        String incorrectPatronymic = "Иванович1337";
        guestToSave.setPatronymic(incorrectPatronymic);
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));
    }

    @Test
    public void whenSaveGuestWithNullAndIncorrectGenderShouldReturnException() {
        Guest guestToSave = new Guest();

        guestToSave.setId(guest1.getId());
        guestToSave.setRoom(room1);
        guestToSave.setFirstName(guest1.getFirstName());
        guestToSave.setLastName(guest1.getLastName());
        guestToSave.setPatronymic(guest1.getPatronymic());
        guestToSave.setDateChange(guest1.getDateChange());
        guestToSave.setDateCreation(guest1.getDateCreation());

        GuestDto guestDtoToSave = mapper.guestToGuestDto(guestToSave);
        Mockito.verify(mapper).guestToGuestDto(guestToSave);

        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));

        String incorrectGenderValue = "test";
        Assertions.assertThrows(ValidationException.class, () -> Gender.fromValue(incorrectGenderValue, "incorrectGenderValue"));

        guestToSave.setGender(Gender.FEMALE);
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));
    }

    @Test
    public void whenSaveGuestWithNoAvailableSeatsShouldReturnException() {
        Guest guestToSave = new Guest();

        room1.setSeatsNumber(0);

        guestToSave.setId(guest1.getId());
        guestToSave.setRoom(room1);
        guestToSave.setGender(guest1.getGender());
        guestToSave.setFirstName(guest1.getFirstName());
        guestToSave.setLastName(guest1.getLastName());
        guestToSave.setPatronymic(guest1.getPatronymic());
        guestToSave.setDateChange(guest1.getDateChange());
        guestToSave.setDateCreation(guest1.getDateCreation());

        GuestDto guestDtoToSave = mapper.guestToGuestDto(guestToSave);
        Mockito.verify(mapper).guestToGuestDto(guestToSave);

        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.saveGuest(guestDtoToSave));
    }

    @Test
    public void shouldReturnGuestWhenUpdateGuest() {
        GuestDto guestDtoToUpdate = mapper.guestToGuestDto(guest1);
        Mockito.verify(mapper).guestToGuestDto(guest1);

        Long guestId = guest1.getId();

        Mockito.when(guestRepository.findById(guestDtoToUpdate.getId())).thenReturn(Optional.of(guest2));
        Mockito.when(guestRepository.save(ArgumentMatchers.any())).thenReturn(guest1);

        Room updatedRoom = guest2.getRoom();
        Mockito.when(roomRepository.findById(guestDtoToUpdate.getRoomId())).thenReturn(Optional.of(updatedRoom));
        //RoomDto updatedRoomDto = mapper.roomToRoomDto(updatedRoom);
        //Mockito.verify(mapper).roomToRoomDto(updatedRoom);

        GuestDto updatedGuestDto = guestServiceImpl.updateGuest(guestDtoToUpdate, guestId);

        Assertions.assertEquals(guest1.getRoom().getId(), updatedGuestDto.getRoomId());

        Mockito.verify(guestRepository).findById(guestDtoToUpdate.getId());
        Mockito.verify(roomRepository).findById(guestDtoToUpdate.getRoomId());
        Mockito.verify(guestRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void shouldReturnExceptionWhenUpdateGuestWithoutChanges() {
        GuestDto guestDtoToUpdate = mapper.guestToGuestDto(guest1);
        Mockito.verify(mapper).guestToGuestDto(guest1);

        Long guestId = guest1.getId();

        Mockito.when(guestRepository.findById(guestDtoToUpdate.getId())).thenReturn(Optional.of(guest2));
        //Mockito.when(guestRepository.save(ArgumentMatchers.any())).thenReturn(guest2);
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.updateGuest(guestDtoToUpdate, guestId));
    }

    @Test
    public void shouldReturnExceptionWhenUpdateGuestWitIncorrectId() {
        GuestDto guestDtoToUpdate = mapper.guestToGuestDto(guest1);
        Mockito.verify(mapper).guestToGuestDto(guest1);

        Long guestId = guest1.getId();
        Mockito.when(guestRepository.findById(guestDtoToUpdate.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.updateGuest(guestDtoToUpdate, guestId));
    }

    @Test
    public void shouldDeleteGuest() {
        Mockito.when(guestRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(guest1));

        Long guestId = guest1.getId();
        guestServiceImpl.deleteGuest(guestId);

        Mockito.verify(guestRepository).findById(ArgumentMatchers.any());
        Mockito.verify(guestRepository).deleteById(guestId);
    }

    @Test
    public void shouldDeleteNotExistedGuestReturnException() {
        Mockito.when(guestRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Long guestId = guest1.getId();
        Assertions.assertThrows(ValidationException.class, () -> guestServiceImpl.deleteGuest(guestId));

        Mockito.verify(guestRepository).findById(ArgumentMatchers.any());
    }

}
