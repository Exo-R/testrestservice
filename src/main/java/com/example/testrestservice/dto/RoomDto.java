package com.example.testrestservice.dto;

import com.example.testrestservice.entity.TypeComfort;
import com.example.testrestservice.entity.TypeRoom;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private Long id;
    private Integer floor;
    private Integer roomNumber;
    private TypeRoom typeRoom;
    private TypeComfort typeComfort;
    private Integer seatsNumber;
    private LocalDate dateCreation;
    private LocalDate dateChange;

}
