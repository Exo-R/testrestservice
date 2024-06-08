package com.example.testrestservice.service;

import com.example.testrestservice.dto.RoomDto;

import java.util.List;

public interface RoomService {

    List<RoomDto> findAllByFilter(String typeRoom, String typeComfort, Boolean isAvailable);

    RoomDto saveRoom(RoomDto roomDto);

    RoomDto updateRoom(RoomDto roomDto, Long roomId);

    void deleteRoom(Long roomId);

}
