package com.example.testrestservice.controller;

import com.example.testrestservice.dto.GuestDto;
import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.entity.Gender;
import com.example.testrestservice.entity.TypeComfort;
import com.example.testrestservice.entity.TypeRoom;
import com.example.testrestservice.service.impl.GuestServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
@AutoConfigureMockMvc
public class GuestControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper mapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @MockBean
    GuestServiceImpl guestServiceImpl;

    private GuestDto guestDto;
    private RoomDto roomDto;

    @BeforeEach
    void setup() {
         roomDto = new RoomDto(
                1L,1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 2, LocalDate.now(), LocalDate.now()
        );
         guestDto = new GuestDto(
                1L, roomDto.getId(), "Иван", "Иванов", "Иванович", Gender.MALE, LocalDate.now(), LocalDate.now()
        );
    }

    @Test
    void findAllByGenderReturnStatusOkAndListGuestDto() throws Exception {

        String gender = "male";

        Mockito
                .when(guestServiceImpl.findAllByFilter(ArgumentMatchers.eq(gender), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(List.of(guestDto));

        mockMvc.perform(get("/guest/findall?gender={gender}", gender)
                        .content(mapper.writeValueAsString(guestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(guestDto.getId()))
                .andExpect(jsonPath("$.[0].roomId").value(guestDto.getRoomId()))
                .andExpect(jsonPath("$.[0].firstName").value(guestDto.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(guestDto.getLastName()))
                .andExpect(jsonPath("$.[0].patronymic").value(guestDto.getPatronymic()))
                .andExpect(jsonPath("$.[0].gender").value(guestDto.getGender().getValue()))
                .andExpect(jsonPath("$.[0].dateCreation").value(guestDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.[0].dateChange").value(guestDto.getDateChange().toString()));

        Mockito.verify(guestServiceImpl, Mockito.times(1))
                .findAllByFilter(ArgumentMatchers.eq(gender), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void findAllByNumberRoomReturnStatusOkAndListGuestDto() throws Exception {

        Integer numberRoom = roomDto.getRoomNumber();

        Mockito
                .when(guestServiceImpl.findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.eq(numberRoom), ArgumentMatchers.any()))
                .thenReturn(List.of(guestDto));

        mockMvc.perform(get("/guest/findall?numberRoom={numberRoom}", numberRoom)
                        .content(mapper.writeValueAsString(guestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(guestDto.getId()))
                .andExpect(jsonPath("$.[0].roomId").value(guestDto.getRoomId()))
                .andExpect(jsonPath("$.[0].firstName").value(guestDto.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(guestDto.getLastName()))
                .andExpect(jsonPath("$.[0].patronymic").value(guestDto.getPatronymic()))
                .andExpect(jsonPath("$.[0].gender").value(guestDto.getGender().getValue()))
                .andExpect(jsonPath("$.[0].dateCreation").value(guestDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.[0].dateChange").value(guestDto.getDateChange().toString()));

        Mockito.verify(guestServiceImpl, Mockito.times(1))
                .findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.eq(numberRoom), ArgumentMatchers.any());
    }

    @Test
    void findAllByTypeComfortReturnStatusOkAndListGuestDto() throws Exception {

        String typeComfort = roomDto.getTypeComfort().getValue();

        Mockito
                .when(guestServiceImpl.findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.eq(typeComfort)))
                .thenReturn(List.of(guestDto));

        mockMvc.perform(get("/guest/findall?typeComfort={typeComfort}", typeComfort)
                        .content(mapper.writeValueAsString(guestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(guestDto.getId()))
                .andExpect(jsonPath("$.[0].roomId").value(guestDto.getRoomId()))
                .andExpect(jsonPath("$.[0].firstName").value(guestDto.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(guestDto.getLastName()))
                .andExpect(jsonPath("$.[0].patronymic").value(guestDto.getPatronymic()))
                .andExpect(jsonPath("$.[0].gender").value(guestDto.getGender().getValue()))
                .andExpect(jsonPath("$.[0].dateCreation").value(guestDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.[0].dateChange").value(guestDto.getDateChange().toString()));

        Mockito.verify(guestServiceImpl, Mockito.times(1))
                .findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.eq(typeComfort));
    }

    @Test
    void saveGuestDtoReturnStatusOkAndGuestDto() throws Exception {
        Mockito.when(guestServiceImpl.saveGuest(ArgumentMatchers.any())).thenReturn(guestDto);

        mockMvc.perform(post("/guest/save")
                        .content(mapper.writeValueAsString(guestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guestDto.getId()))
                .andExpect(jsonPath("$.roomId").value(guestDto.getRoomId()))
                .andExpect(jsonPath("$.firstName").value(guestDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(guestDto.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(guestDto.getPatronymic()))
                .andExpect(jsonPath("$.gender").value(guestDto.getGender().getValue()))
                .andExpect(jsonPath("$.dateCreation").value(guestDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.dateChange").value(guestDto.getDateChange().toString()));

        Mockito.verify(guestServiceImpl, Mockito.times(1)).saveGuest(ArgumentMatchers.any());
    }

    @Test
    void updateGuestDtoReturnStatusOkAndGuestDto() throws Exception {
        Mockito.when(guestServiceImpl.updateGuest(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(guestDto);

        mockMvc.perform(patch("/guest/{guestId}", guestDto.getId())
                        .content(mapper.writeValueAsString(guestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guestDto.getId()))
                .andExpect(jsonPath("$.roomId").value(guestDto.getRoomId()))
                .andExpect(jsonPath("$.firstName").value(guestDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(guestDto.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(guestDto.getPatronymic()))
                .andExpect(jsonPath("$.gender").value(guestDto.getGender().getValue()))
                .andExpect(jsonPath("$.dateCreation").value(guestDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.dateChange").value(guestDto.getDateChange().toString()));

        Mockito.verify(guestServiceImpl, Mockito.times(1)).updateGuest(ArgumentMatchers.any(), ArgumentMatchers.anyLong());
    }

    @Test
    void deleteGuestDtoReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/guest/{guestId}", guestDto.getId())
                        .content(mapper.writeValueAsString(guestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(guestServiceImpl, Mockito.times(1)).deleteGuest(ArgumentMatchers.anyLong());
    }

}
