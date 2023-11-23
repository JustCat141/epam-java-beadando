package com.epam.training.ticketservice.core.screening.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening,Long> {
    List<Screening> findAllByRoomName(String name);
}
