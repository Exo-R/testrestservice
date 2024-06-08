package com.example.testrestservice.controller;

import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @GetMapping("/findall")
    public List<RoomDto> findAll(
            @QueryParam("typeRoom") String typeRoom,
            @QueryParam("typeComfort") String typeComfort,
            @QueryParam("isAvailable") Boolean isAvailable
    ) {
        return roomService.findAllByFilter(typeRoom, typeComfort, isAvailable);
    }

    @PostMapping("/save")
    public ResponseEntity<RoomDto> save(@RequestBody RoomDto roomDto) {
        return ResponseEntity.ok().body(roomService.saveRoom(roomDto));
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<RoomDto> update(@RequestBody RoomDto roomDto, @PathVariable(name = "roomId") Long roomId) {
        return ResponseEntity.ok().body(roomService.updateRoom(roomDto, roomId));
    }

    @DeleteMapping("/{roomId}")
    public Response delete(@PathVariable(name = "roomId") Long roomId) {
        roomService.deleteRoom(roomId);
        return Response.ok().build();
    }

}
