package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {
    ScreeningDto createScreening(String movieTitle, String roomName, String startOfScreening);
    void uploadScreening(ScreeningDto screeningDto);

    boolean isOverlapping(ScreeningDto screeningDto, int additionalTime);

    Optional<ScreeningDto> deleteScreening(String movieTitle,String roomName, LocalDateTime startOfScreening);
}
