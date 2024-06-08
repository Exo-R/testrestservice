package com.example.testrestservice.controller;

import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.entity.TypeComfort;
import com.example.testrestservice.entity.TypeRoom;
import com.example.testrestservice.service.impl.RoomServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc
public class RoomControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RoomServiceImpl roomServiceImpl;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    RoomDto roomDto;

    @BeforeEach
    void setup() {
        roomDto = new RoomDto(
                1L,1, 3, TypeRoom.MALE, TypeComfort.STANDARD, 2, LocalDate.now(), LocalDate.now()
        );
    }

    @Test
    void findAllByTypeRoomReturnStatusOkAndListRoomDto() throws Exception {

        String typeRoom = roomDto.getTypeRoom().getValue();

        Mockito
                .when(roomServiceImpl.findAllByFilter(ArgumentMatchers.eq(typeRoom), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(List.of(roomDto));

        mockMvc.perform(get("/room/findall?typeRoom={typeRoom}", typeRoom)
                        .content(mapper.writeValueAsString(roomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(roomDto.getId()))
                .andExpect(jsonPath("$.[0].floor").value(roomDto.getFloor()))
                .andExpect(jsonPath("$.[0].roomNumber").value(roomDto.getRoomNumber()))
                .andExpect(jsonPath("$.[0].typeRoom").value(roomDto.getTypeRoom().toString()))
                .andExpect(jsonPath("$.[0].typeComfort").value(roomDto.getTypeComfort().toString()))
                .andExpect(jsonPath("$.[0].seatsNumber").value(roomDto.getSeatsNumber()))
                .andExpect(jsonPath("$.[0].dateCreation").value(roomDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.[0].dateChange").value(roomDto.getDateChange().toString()));

        Mockito.verify(roomServiceImpl, Mockito.times(1))
                .findAllByFilter(ArgumentMatchers.eq(typeRoom), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void findAllByTypeComfortReturnStatusOkAndListRoomDto() throws Exception {

        String typeComfort = roomDto.getTypeComfort().getValue();

        Mockito
                .when(roomServiceImpl.findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.eq(typeComfort), ArgumentMatchers.any()))
                .thenReturn(List.of(roomDto));

        mockMvc.perform(get("/room/findall?typeComfort={typeComfort}", typeComfort)
                        .content(mapper.writeValueAsString(roomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(roomDto.getId()))
                .andExpect(jsonPath("$.[0].floor").value(roomDto.getFloor()))
                .andExpect(jsonPath("$.[0].roomNumber").value(roomDto.getRoomNumber()))
                .andExpect(jsonPath("$.[0].typeRoom").value(roomDto.getTypeRoom().toString()))
                .andExpect(jsonPath("$.[0].typeComfort").value(roomDto.getTypeComfort().toString()))
                .andExpect(jsonPath("$.[0].seatsNumber").value(roomDto.getSeatsNumber()))
                .andExpect(jsonPath("$.[0].dateCreation").value(roomDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.[0].dateChange").value(roomDto.getDateChange().toString()));

        Mockito.verify(roomServiceImpl, Mockito.times(1))
                .findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.eq(typeComfort), ArgumentMatchers.any());
    }

    @Test
    void findAllIfIsAvailableReturnStatusOkAndListRoomDto() throws Exception {

        Boolean isAvailable = true;

        Mockito
                .when(roomServiceImpl.findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.eq(isAvailable)))
                .thenReturn(List.of(roomDto));

        mockMvc.perform(get("/room/findall?isAvailable={isAvailable}", isAvailable)
                        .content(mapper.writeValueAsString(roomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(roomDto.getId()))
                .andExpect(jsonPath("$.[0].floor").value(roomDto.getFloor()))
                .andExpect(jsonPath("$.[0].roomNumber").value(roomDto.getRoomNumber()))
                .andExpect(jsonPath("$.[0].typeRoom").value(roomDto.getTypeRoom().toString()))
                .andExpect(jsonPath("$.[0].typeComfort").value(roomDto.getTypeComfort().toString()))
                .andExpect(jsonPath("$.[0].seatsNumber").value(roomDto.getSeatsNumber()))
                .andExpect(jsonPath("$.[0].dateCreation").value(roomDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.[0].dateChange").value(roomDto.getDateChange().toString()));

        Mockito.verify(roomServiceImpl, Mockito.times(1))
                .findAllByFilter(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.eq(isAvailable));
    }

    @Test
    void saveRoomDtoReturnStatusOkAndRoomDto() throws Exception {
        Mockito.when(roomServiceImpl.saveRoom(ArgumentMatchers.any())).thenReturn(roomDto);

        mockMvc.perform(post("/room/save")
                        .content(mapper.writeValueAsString(roomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomDto.getId()))
                .andExpect(jsonPath("$.floor").value(roomDto.getFloor()))
                .andExpect(jsonPath("$.roomNumber").value(roomDto.getRoomNumber()))
                .andExpect(jsonPath("$.typeRoom").value(roomDto.getTypeRoom().toString()))
                .andExpect(jsonPath("$.typeComfort").value(roomDto.getTypeComfort().toString()))
                .andExpect(jsonPath("$.seatsNumber").value(roomDto.getSeatsNumber()))
                .andExpect(jsonPath("$.dateCreation").value(roomDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.dateChange").value(roomDto.getDateChange().toString()));

        Mockito.verify(roomServiceImpl, Mockito.times(1)).saveRoom(ArgumentMatchers.any());
    }

    @Test
    void updateRoomDtoReturnStatusOkAndRoomDto() throws Exception {
        Mockito.when(roomServiceImpl.updateRoom(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(roomDto);

        mockMvc.perform(patch("/room/{roomId}", roomDto.getId())
                        .content(mapper.writeValueAsString(roomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomDto.getId()))
                .andExpect(jsonPath("$.floor").value(roomDto.getFloor()))
                .andExpect(jsonPath("$.roomNumber").value(roomDto.getRoomNumber()))
                .andExpect(jsonPath("$.typeRoom").value(roomDto.getTypeRoom().toString()))
                .andExpect(jsonPath("$.typeComfort").value(roomDto.getTypeComfort().toString()))
                .andExpect(jsonPath("$.seatsNumber").value(roomDto.getSeatsNumber()))
                .andExpect(jsonPath("$.dateCreation").value(roomDto.getDateCreation().toString()))
                .andExpect(jsonPath("$.dateChange").value(roomDto.getDateChange().toString()));

        Mockito.verify(roomServiceImpl, Mockito.times(1)).updateRoom(ArgumentMatchers.any(), ArgumentMatchers.anyLong());
    }

   @Test
    void deleteRoomDtoReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/room/{roomId}", roomDto.getId())
                        .content(mapper.writeValueAsString(roomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(roomServiceImpl, Mockito.times(1)).deleteRoom(ArgumentMatchers.anyLong());
    }

}