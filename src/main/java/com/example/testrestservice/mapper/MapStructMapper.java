package com.example.testrestservice.mapper;

import com.example.testrestservice.dto.GuestDto;
import com.example.testrestservice.dto.RoomDto;
import com.example.testrestservice.entity.Guest;
import com.example.testrestservice.entity.Room;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        //nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
        //componentModel = "spring"
)
public interface MapStructMapper {

    MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

    RoomDto roomToRoomDto(Room room);
    Room RoomDtoToRoom(RoomDto roomDto);
    @Mapping(target = "roomId", source = "guest.room.id") //@Mapping(target = "roomId", source = "room.id")
    GuestDto guestToGuestDto(Guest guest);
    @Mapping(target = "room.id", source = "guestDto.roomId") //@Mapping(target = "id", source = "guestDto.roomId")
    Guest guestDtoToGuest(GuestDto guestDto);
}
