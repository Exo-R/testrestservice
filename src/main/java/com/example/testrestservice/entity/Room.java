package com.example.testrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_room")
    private TypeRoom typeRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_comfort")
    private TypeComfort typeComfort;

    @Column(name = "seats_number")
    private Integer seatsNumber;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_change")
    private LocalDate dateChange;

}
