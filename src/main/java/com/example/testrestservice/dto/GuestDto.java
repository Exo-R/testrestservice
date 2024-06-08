package com.example.testrestservice.dto;

import com.example.testrestservice.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestDto {

    private Long id;
    private Long roomId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Gender gender;
    private LocalDate dateCreation;
    private LocalDate dateChange;

}
