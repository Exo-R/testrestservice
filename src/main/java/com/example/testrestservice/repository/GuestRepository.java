package com.example.testrestservice.repository;

import com.example.testrestservice.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Integer countGuestByRoom_Id(Long id);

}
