package com.example.testrestservice.service;

import com.example.testrestservice.dto.GuestDto;

import java.util.List;

public interface GuestService {

    List<GuestDto> findAllByFilter(String gender, Integer numberRoom, String typeComfort);

    GuestDto saveGuest(GuestDto guestDto);

    GuestDto updateGuest(GuestDto guestDto, Long guestId);

    void deleteGuest(Long guestId);

}
