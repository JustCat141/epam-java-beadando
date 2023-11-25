package com.epam.training.ticketservice.core.screening.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening,Long> {
    List<Screening> findAllByRoomName(String name);

    Optional<Screening> findByMovieTitleAndRoomNameAndStartOfScreening(
            String movieTitle,
            String roomName,
            LocalDateTime startOfScreening);
}