package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.persistence.Screening;

import java.time.Duration;
import java.time.LocalDateTime;

public record ScreeningDto(Movie movie, Room room, LocalDateTime startOfScreening) {
    public Integer screeningLength() {
        return movie.getLength();
    }

    public LocalDateTime endOfScreening() {
        return startOfScreening.plusMinutes(screeningLength());
    }

    public Screening asEntity() {
        return new Screening(movie, room, startOfScreening);
    }
}