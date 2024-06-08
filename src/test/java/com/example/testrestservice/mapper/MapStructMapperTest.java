package com.example.testrestservice.mapper;

import com.example.testrestservice.dto.GuestDto;
import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MapStructMapperTest {

    @Test
    void RoomDtoToRoom() {
        RoomDto roomDto = new RoomDto(
                1L,1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 1, LocalDate.now(), LocalDate.now()
        );
        Room room = MapStructMapper.INSTANCE.RoomDtoToRoom(roomDto);

        Assertions.assertNotNull(room);
        Assertions.assertEquals(roomDto.getId(), room.getId());
        Assertions.assertEquals(roomDto.getTypeRoom(), room.getTypeRoom());
        Assertions.assertEquals(roomDto.getRoomNumber(), room.getRoomNumber());
        Assertions.assertEquals(roomDto.getTypeComfort(), room.getTypeComfort());
        Assertions.assertEquals(roomDto.getSeatsNumber(), room.getSeatsNumber());
        Assertions.assertEquals(roomDto.getFloor(), room.getFloor());
        Assertions.assertEquals(roomDto.getDateChange(), room.getDateChange());
        Assertions.assertEquals(roomDto.getDateCreation(), room.getDateCreation());
    }

    @Test
    void roomToRoomDto() {
        Room room = new Room(
                1L,1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 1, LocalDate.now(), LocalDate.now()
        );
        RoomDto roomDto = MapStructMapper.INSTANCE.roomToRoomDto(room);

        Assertions.assertNotNull(room);
        Assertions.assertEquals(room.getId(), roomDto.getId());
        Assertions.assertEquals(room.getTypeRoom(), roomDto.getTypeRoom());
        Assertions.assertEquals(room.getRoomNumber(), roomDto.getRoomNumber());
        Assertions.assertEquals(room.getTypeComfort(), roomDto.getTypeComfort());
        Assertions.assertEquals(room.getSeatsNumber(), roomDto.getSeatsNumber());
        Assertions.assertEquals(room.getFloor(), roomDto.getFloor());
        Assertions.assertEquals(room.getDateChange(), roomDto.getDateChange());
        Assertions.assertEquals(room.getDateCreation(), roomDto.getDateCreation());
    }

    @Test
    void toGuest_FromGuestDto() {
        RoomDto roomDto = new RoomDto(
                1L,1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 1, LocalDate.now(), LocalDate.now()
        );
        GuestDto guestDto = new GuestDto(
                1L, roomDto.getId(), "Иван", "Иванов", "Иванович", Gender.MALE, LocalDate.now(), LocalDate.now()
        );

        Guest guest = MapStructMapper.INSTANCE.guestDtoToGuest(guestDto);

        Assertions.assertNotNull(guest);
        Assertions.assertEquals(guestDto.getId(), guest.getId());
        Assertions.assertEquals(guestDto.getRoomId(), guest.getRoom().getId());
        Assertions.assertEquals(guestDto.getFirstName(), guest.getFirstName());
        Assertions.assertEquals(guestDto.getLastName(), guest.getLastName());
        Assertions.assertEquals(guestDto.getPatronymic(), guest.getPatronymic());
        Assertions.assertEquals(guestDto.getDateChange(), guest.getDateChange());
        Assertions.assertEquals(guestDto.getDateCreation(), guest.getDateCreation());
    }

    @Test
    void toGuestDto_FromGuest() {
        Room room = new Room(
                1L,1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 1, LocalDate.now(), LocalDate.now()
        );
        Guest guest = new Guest(
                1L, room, "Иван", "Иванов", "Иванович", Gender.MALE, LocalDate.now(), LocalDate.now()
        );

        GuestDto guestDto = MapStructMapper.INSTANCE.guestToGuestDto(guest);

        Assertions.assertNotNull(guest);
        Assertions.assertEquals(guest.getId(), guestDto.getId());
        Assertions.assertEquals(guest.getRoom().getId(), guestDto.getId());
        Assertions.assertEquals(guest.getFirstName(), guestDto.getFirstName());
        Assertions.assertEquals(guest.getLastName(), guestDto.getLastName());
        Assertions.assertEquals(guest.getPatronymic(), guestDto.getPatronymic());
        Assertions.assertEquals(guest.getDateChange(), guestDto.getDateChange());
        Assertions.assertEquals(guest.getDateCreation(), guestDto.getDateCreation());
    }

}
