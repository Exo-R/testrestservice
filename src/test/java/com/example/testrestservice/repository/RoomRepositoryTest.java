package com.example.testrestservice.repository;

import com.example.testrestservice.entity.Room;
import com.example.testrestservice.entity.TypeComfort;
import com.example.testrestservice.entity.TypeRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTest {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    GuestRepository guestRepository;

    private Room room;

    @BeforeEach
    void setup() {
        room = addRoom(
                1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 1, LocalDate.now(), LocalDate.now()
        );
    }

    @Test
    void findAll() {
        List<Room> list = roomRepository.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());

        Assertions.assertNotNull(list.get(0));
        Assertions.assertEquals(room.getId(), list.get(0).getId());
    }

    @Test
    void findByRoomNumber() {
        Optional<Room> foundRoom = roomRepository.findByRoomNumber(room.getRoomNumber());

        Assertions.assertTrue(foundRoom.isPresent());
        Assertions.assertEquals(room.getId(), foundRoom.get().getId());
    }

    @Test
    void findById() {
        Optional<Room> foundRoom = roomRepository.findById(room.getId());

        Assertions.assertTrue(foundRoom.isPresent());
        Assertions.assertEquals(room.getId(), foundRoom.get().getId());
    }

    private Room addRoom(
            Integer floor,
            Integer roomNumber,
            TypeRoom typeRoom,
            TypeComfort typeComfort,
            Integer seatsNumber,
            LocalDate dateCreation,
            LocalDate dateChange
    ) {
        Room room = new Room();
        room.setFloor(floor);
        room.setRoomNumber(roomNumber);
        room.setTypeRoom(typeRoom);
        room.setTypeComfort(typeComfort);
        room.setSeatsNumber(seatsNumber);
        room.setDateCreation(dateCreation);
        room.setDateChange(dateChange);
        return roomRepository.save(room);
    }

}