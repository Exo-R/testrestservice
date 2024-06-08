package com.example.testrestservice.repository;

import com.example.testrestservice.entity.*;
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
public class GuestRepositoryTest {

    @Autowired
    GuestRepository guestRepository;
    @Autowired
    RoomRepository roomRepository;

    Guest guest;
    Room room;

    @BeforeEach
    void setup() {
        room = addRoom(
                1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 1, LocalDate.now(), LocalDate.now()
        );
        guest = addGuest(
                room, "Иван", "Иванов", "Иванович", Gender.MALE, LocalDate.now(), LocalDate.now()
        );
    }

    @Test
    void findAll() {
        List<Guest> list = guestRepository.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());

        Assertions.assertNotNull(list.get(0));
        Assertions.assertEquals(guest.getId(), list.get(0).getId());
        Assertions.assertEquals(room.getId(), list.get(0).getRoom().getId());
    }

    @Test
    void findById() {
        Optional<Guest> foundGuest = guestRepository.findById(guest.getId());

        Assertions.assertTrue(foundGuest.isPresent());
        Assertions.assertEquals(guest.getId(), foundGuest.get().getId());
    }

    @Test
    void countGuestByRoom_Id() {
        Integer count = guestRepository.countGuestByRoom_Id(room.getId());

        Assertions.assertNotNull(count);
        Assertions.assertEquals(1, count);
    }

    private Guest addGuest(
            Room room,
            String firstName,
            String lastName,
            String patronymic,
            Gender gender,
            LocalDate dateChange,
            LocalDate dateCreation
    ) {
        guest = new Guest();
        guest.setRoom(room);
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setPatronymic(patronymic);
        guest.setGender(gender);
        guest.setDateChange(dateChange);
        guest.setDateCreation(dateCreation);
        return guestRepository.save(guest);
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
