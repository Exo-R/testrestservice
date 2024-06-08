package com.example.testrestservice.controller;

import com.example.testrestservice.dto.GuestDto;
import com.example.testrestservice.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/guest")
@AllArgsConstructor
public class GuestController {

    private final GuestService guestService;


    @GetMapping("/findall")
    public List<GuestDto> findAll(
            @QueryParam("gender") String gender,
            @QueryParam("numberRoom") Integer numberRoom,
            @QueryParam("typeComfort") String typeComfort
    ) {
        return guestService.findAllByFilter(gender, numberRoom, typeComfort);
    }

    @PostMapping("/save")
    public ResponseEntity<GuestDto> save(@RequestBody GuestDto guestDto) {
        return ResponseEntity.ok().body(guestService.saveGuest(guestDto));
    }

    @PatchMapping("/{guestId}")
    public ResponseEntity<GuestDto> update(@RequestBody GuestDto guestDto, @PathVariable(name = "guestId") Long guestId) {
        return ResponseEntity.ok().body(guestService.updateGuest(guestDto, guestId));
    }

    @DeleteMapping("/{guestId}")
    public Response delete(@PathVariable(name = "guestId") Long guestId) {
        guestService.deleteGuest(guestId);
        return Response.ok().build();
    }

}
