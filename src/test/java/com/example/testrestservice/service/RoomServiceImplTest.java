package com.example.testrestservice.service;

import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.entity.Room;
import com.example.testrestservice.entity.TypeComfort;
import com.example.testrestservice.entity.TypeRoom;
import com.example.testrestservice.exception.ValidationException;
import com.example.testrestservice.mapper.MapStructMapperImpl;
import com.example.testrestservice.repository.GuestRepository;
import com.example.testrestservice.repository.RoomRepository;
import com.example.testrestservice.service.impl.RoomServiceImpl;
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
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private  GuestRepository guestRepository;
    @Spy
    private MapStructMapperImpl mapper;
    @InjectMocks
    private RoomServiceImpl roomServiceImpl;

    private Room room1;
    private Room room2;


    @BeforeEach
    void setup() {
        room1 = new Room(1L, 1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 2, LocalDate.now(), LocalDate.now());
        room2 = new Room(3L, 3, 9, TypeRoom.FEMALE, TypeComfort.LUX, 1, LocalDate.now(), LocalDate.now());
    }

    private List<Room> getRooms() {
        return List.of(room1, room2);
    }

    @Test
    public void shouldReturnAllRoomsWhenCallFindAll() {
        List<Room> listRooms = getRooms();

        Mockito.when(roomRepository.findAll()).thenReturn(listRooms);

        String typeRoom = null;
        String typeComfort = null;
        Boolean isAvailable = null;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);
        Mockito.verify(mapper, Mockito.times(listRooms.size())).roomToRoomDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(listRooms.size(), result.size());
        Assertions.assertEquals(listRooms.get(0).getId(), result.get(0).getId());
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAll() {
        Mockito.when(roomRepository.findAll()).thenReturn(List.of());

        String typeRoom = null;
        String typeComfort = null;
        Boolean isAvailable = null;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    public void shouldReturnAllRoomsWhenCallFindAllByTypeRoom() {
        List<Room> listRooms = getRooms();

        Mockito.when(roomRepository.findAll()).thenReturn(listRooms);

        String typeRoom = room1.getTypeRoom().getValue();
        String typeComfort = null;
        Boolean isAvailable = null;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);
        Mockito.verify(mapper, Mockito.times(1)).roomToRoomDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(listRooms.get(0).getId(), result.get(0).getId());
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAllByTypeRoom() {
        Mockito.when(roomRepository.findAll()).thenReturn(List.of());

        String typeRoom = room1.getTypeRoom().getValue();
        String typeComfort = null;
        Boolean isAvailable = null;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    public void shouldReturnAllRoomsWhenCallFindAllByTypeComfort() {
        List<Room> listRooms = getRooms();

        Mockito.when(roomRepository.findAll()).thenReturn(listRooms);

        String typeRoom = null;
        String typeComfort = room1.getTypeComfort().getValue();
        Boolean isAvailable = null;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);
        Mockito.verify(mapper, Mockito.times(1)).roomToRoomDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(listRooms.get(0).getId(), result.get(0).getId());
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAllByTypeComfort() {
        Mockito.when(roomRepository.findAll()).thenReturn(List.of());

        String typeRoom = null;
        String typeComfort = room1.getTypeComfort().getValue();
        Boolean isAvailable = null;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    public void shouldReturnAllRoomsWhenCallFindAllRoomsWithAvailableSeat() {
        List<Room> listRooms = getRooms();

        Mockito.when(roomRepository.findAll()).thenReturn(listRooms);
        Mockito.when(guestRepository.countGuestByRoom_Id(Mockito.anyLong())).thenReturn(1);

        String typeRoom = null;
        String typeComfort = null;
        Boolean isAvailable = true;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);
        Mockito.verify(mapper, Mockito.times(1)).roomToRoomDto(ArgumentMatchers.any());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(listRooms.get(0).getId(), result.get(0).getId());

        Mockito.verify(roomRepository).findAll();
        Mockito.verify(guestRepository, Mockito.times(listRooms.size())).countGuestByRoom_Id(Mockito.anyLong());
    }

    @Test
    public void shouldReturnEmptyWhenCallFindAllRoomsWithAvailableSeat() {
        Mockito.when(roomRepository.findAll()).thenReturn(List.of());

        String typeRoom = null;
        String typeComfort = null;
        Boolean isAvailable = true;

        List<RoomDto> result = roomServiceImpl.findAllByFilter(typeRoom, typeComfort, isAvailable);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(roomRepository).findAll();
    }
                                                           // изменить названия методов на болеее корректное
    @Test                                                  // удалить везде в сервисах суффикс "room" и "guest"
    public void whenSaveRoomShouldReturnCorrectRoomDto() { // проверить везде маппер и также для геттеров - когда пусто или искл.
        RoomDto roomDtoToSave = mapper.roomToRoomDto(room1);
        Mockito.verify(mapper).roomToRoomDto(room1);

        Mockito.when(roomRepository.save(ArgumentMatchers.any())).thenReturn(room1);

        RoomDto roomDtoSaved = roomServiceImpl.saveRoom(roomDtoToSave);
        Mockito.verify(roomRepository).save(ArgumentMatchers.any());

        Assertions.assertEquals(room1.getId(), roomDtoSaved.getId());
        Assertions.assertEquals(room1.getTypeRoom(), roomDtoSaved.getTypeRoom());
        Assertions.assertEquals(room1.getRoomNumber(), roomDtoSaved.getRoomNumber());
        Assertions.assertEquals(room1.getTypeComfort(), roomDtoSaved.getTypeComfort());
        Assertions.assertEquals(room1.getSeatsNumber(), roomDtoSaved.getSeatsNumber());
        Assertions.assertEquals(room1.getFloor(), roomDtoSaved.getFloor());
        Assertions.assertEquals(room1.getDateChange(), roomDtoSaved.getDateChange());
        Assertions.assertEquals(room1.getDateCreation(), roomDtoSaved.getDateCreation());
    }

    @Test
    public void whenSaveEmptyShouldReturnValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(new RoomDto()));
    }

    @Test
    public void whenSaveRoomWithNullAndIncorrectFloorShouldReturnException() {
        Room roomToSave = new Room();

        roomToSave.setId(room1.getId());
        roomToSave.setTypeRoom(room1.getTypeRoom());
        roomToSave.setTypeComfort(room1.getTypeComfort());
        roomToSave.setRoomNumber(room1.getRoomNumber());
        roomToSave.setSeatsNumber(room1.getSeatsNumber());
        roomToSave.setDateChange(room1.getDateChange());
        roomToSave.setDateCreation(room1.getDateCreation());

        RoomDto roomDtoToSave = mapper.roomToRoomDto(roomToSave);
        Mockito.verify(mapper).roomToRoomDto(roomToSave);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));

        Integer ValueBiggerThanMax = 11;
        roomToSave.setFloor(ValueBiggerThanMax);
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));

        Integer ValueLessThanMin = 0;
        roomToSave.setFloor(ValueLessThanMin);
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));
    }

    @Test
    public void whenSaveRoomWithNullAndIncorrectRoomNumberShouldReturnException() {
        Room roomToSave = new Room();

        roomToSave.setId(room1.getId());
        roomToSave.setTypeRoom(room1.getTypeRoom());
        roomToSave.setTypeComfort(room1.getTypeComfort());
        roomToSave.setFloor(room1.getFloor());
        roomToSave.setSeatsNumber(room1.getSeatsNumber());
        roomToSave.setDateChange(room1.getDateChange());
        roomToSave.setDateCreation(room1.getDateCreation());

        RoomDto roomDtoToSave = mapper.roomToRoomDto(roomToSave);
        Mockito.verify(mapper).roomToRoomDto(roomToSave);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));

        Integer ValueBiggerThanMax = 101;
        roomToSave.setRoomNumber(ValueBiggerThanMax);
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));

        Integer ValueLessThanMin = 0;
        roomToSave.setRoomNumber(ValueLessThanMin);
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));
    }

    @Test
    public void whenSaveRoomWithNullAndIncorrectTypeComfortShouldReturnException() {
        Room roomToSave = new Room();

        roomToSave.setId(room1.getId());
        roomToSave.setTypeRoom(room1.getTypeRoom());
        roomToSave.setRoomNumber(room1.getRoomNumber());
        roomToSave.setFloor(room1.getFloor());
        roomToSave.setSeatsNumber(room1.getSeatsNumber());
        roomToSave.setDateChange(room1.getDateChange());
        roomToSave.setDateCreation(room1.getDateCreation());

        RoomDto roomDtoToSave = mapper.roomToRoomDto(roomToSave);
        Mockito.verify(mapper).roomToRoomDto(roomToSave);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));

        String incorrectTypeComfortValue = "test";
        Assertions.assertThrows(ValidationException.class, () -> TypeComfort.fromValue(incorrectTypeComfortValue, "incorrectTypeComfortValue"));
    }

    @Test
    public void whenSaveRoomWithNullAndIncorrectTypeRoomShouldReturnException() {
        Room roomToSave = new Room();

        roomToSave.setId(room1.getId());
        roomToSave.setTypeRoom(room1.getTypeRoom());
        roomToSave.setRoomNumber(room1.getRoomNumber());
        roomToSave.setFloor(room1.getFloor());
        roomToSave.setSeatsNumber(room1.getSeatsNumber());
        roomToSave.setDateChange(room1.getDateChange());
        roomToSave.setDateCreation(room1.getDateCreation());

        RoomDto roomDtoToSave = mapper.roomToRoomDto(roomToSave);
        Mockito.verify(mapper).roomToRoomDto(roomToSave);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));

        String incorrectTypeRoomValue = "test";
        Assertions.assertThrows(ValidationException.class, () -> TypeRoom.fromValue(incorrectTypeRoomValue, "incorrectTypeRoomValue"));
    }

    @Test
    public void whenSaveRoomWithNullAndIncorrectSeatsNumberShouldReturnException() {
        Room roomToSave = new Room();

        roomToSave.setId(room1.getId());                            // нужно ли оставлять эту строку во всех проверках
        roomToSave.setTypeRoom(room1.getTypeRoom());
        roomToSave.setRoomNumber(room1.getRoomNumber());
        roomToSave.setFloor(room1.getFloor());
        roomToSave.setTypeComfort(room1.getTypeComfort());
        roomToSave.setDateChange(room1.getDateChange());
        roomToSave.setDateCreation(room1.getDateCreation());

        RoomDto roomDtoToSave = mapper.roomToRoomDto(roomToSave);
        Mockito.verify(mapper).roomToRoomDto(roomToSave);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));

        Integer incorrectValue = 0;
        roomToSave.setSeatsNumber(incorrectValue);
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.saveRoom(roomDtoToSave));
    }

    @Test
    public void shouldReturnRoomWhenUpdateRoom() {
        RoomDto roomDtoToUpdate = mapper.roomToRoomDto(room1);
        Mockito.verify(mapper).roomToRoomDto(room1);

        Long roomId = room1.getId();

        Mockito.when(roomRepository.findById(roomDtoToUpdate.getId())).thenReturn(Optional.of(room2));
        Mockito.when(roomRepository.save(ArgumentMatchers.any())).thenReturn(room1); //room1

        RoomDto updatedRoomDto = roomServiceImpl.updateRoom(roomDtoToUpdate, roomId);

        Assertions.assertEquals(room1.getTypeRoom(), updatedRoomDto.getTypeRoom());
        Assertions.assertEquals(room1.getTypeComfort(), updatedRoomDto.getTypeComfort());
        Assertions.assertEquals(room1.getSeatsNumber(), updatedRoomDto.getSeatsNumber());

        Mockito.verify(roomRepository).findById(roomDtoToUpdate.getId());
        Mockito.verify(roomRepository).save(ArgumentMatchers.any());
    }

    @Test
    public void shouldReturnExceptionWhenUpdateRoomWithoutChanges() {
        RoomDto roomDtoToUpdate = mapper.roomToRoomDto(room1);
        Mockito.verify(mapper).roomToRoomDto(room1);

        Long roomId = room1.getId();

        Mockito.when(roomRepository.findById(roomId)).thenReturn(Optional.of(room1));

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.updateRoom(roomDtoToUpdate, roomId));
    }

    @Test
    public void shouldReturnExceptionWhenUpdateRoomWithIncorrectId() {

        RoomDto roomDtoToUpdate = mapper.roomToRoomDto(room1);
        Mockito.verify(mapper).roomToRoomDto(room1);

        Long roomId = room1.getId();

        Mockito.when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        //Mockito.when(roomRepository.save(ArgumentMatchers.any())).thenReturn(room1);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.updateRoom(roomDtoToUpdate, roomId));
        Mockito.verify(roomRepository).findById(ArgumentMatchers.any());
    }

    @Test
    public void shouldReturnExceptionWhenUpdateRoomWithEmptyData() {

        room1.setTypeRoom(null);

        RoomDto roomDtoToUpdate = mapper.roomToRoomDto(room1);
        Mockito.verify(mapper).roomToRoomDto(room1);

        Long roomId = room1.getId();

        Mockito.when(roomRepository.findById(roomId)).thenReturn(Optional.of(room1));

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.updateRoom(roomDtoToUpdate, roomId));

        room1.setTypeRoom(room2.getTypeRoom());
        room1.setTypeComfort(null);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.updateRoom(roomDtoToUpdate, roomId));

        room1.setTypeComfort(room2.getTypeComfort());
        room1.setSeatsNumber(null);

        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.updateRoom(roomDtoToUpdate, roomId));

        Mockito.verify(roomRepository, Mockito.times(3)).findById(ArgumentMatchers.any());
    }

    @Test
    public void shouldReturnExceptionWhenDeleteRoom() {
        Mockito.when(roomRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Long roomId = 1L;
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.deleteRoom(roomId));

        Mockito.when(roomRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(room1));

        Integer incorrectCount = 1;
        Mockito.when(guestRepository.countGuestByRoom_Id(ArgumentMatchers.any())).thenReturn(incorrectCount);
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.deleteRoom(roomId));

        Mockito.verify(roomRepository, Mockito.times(2)).findById(ArgumentMatchers.any());
        Mockito.verify(guestRepository).countGuestByRoom_Id(ArgumentMatchers.any());
    }

    @Test
    public void shouldDeleteRoom() {
        Mockito.when(roomRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(room1));

        Integer correctCount = 0;
        Mockito.when(guestRepository.countGuestByRoom_Id(ArgumentMatchers.any())).thenReturn(correctCount);

        Long roomId = 1L;
        roomServiceImpl.deleteRoom(roomId);

        Mockito.verify(roomRepository).findById(ArgumentMatchers.any());
        Mockito.verify(guestRepository).countGuestByRoom_Id(ArgumentMatchers.any());
        Mockito.verify(roomRepository).deleteById(roomId);
    }

    @Test
    public void shouldDeleteNotExistedRoomReturnException() {
        Mockito.when(roomRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Long roomId = 1L;
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.deleteRoom(roomId));

        Mockito.verify(roomRepository).findById(ArgumentMatchers.any());
    }

    @Test
    public void shouldDeleteRoomWithGuestsReturnException() {
        Mockito.when(roomRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(room1));

        Integer incorrectCount = 1;
        Mockito.when(guestRepository.countGuestByRoom_Id(ArgumentMatchers.any())).thenReturn(incorrectCount);

        Long roomId = 1L;
        Assertions.assertThrows(ValidationException.class, () -> roomServiceImpl.deleteRoom(roomId));

        Mockito.verify(roomRepository).findById(ArgumentMatchers.any());
        Mockito.verify(guestRepository).countGuestByRoom_Id(ArgumentMatchers.any());
    }

}
