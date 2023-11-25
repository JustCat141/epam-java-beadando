package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScreeningTest {

    private final Movie movie = new Movie("movie","asd",60);
    private final Room room = new Room("imax",20,20);

    @Test
    void testScreeningShouldReturnDto() {
        var screening = new Screening(movie,room, LocalDateTime.now());

        var screeningDto = screening.asDto();

        assertEquals(screeningDto.movie(),screening.getMovie());
        assertEquals(screeningDto.room(),screening.getRoom());
        assertEquals(screeningDto.startOfScreening(),screening.getStartOfScreening());
    }

    @Test
    void testScreeningShouldReturnEntity() {
        var screeningDto = new ScreeningDto(movie,room, LocalDateTime.now());

        var screening = screeningDto.asEntity();

        assertEquals(screeningDto.movie(),screening.getMovie());
        assertEquals(screeningDto.room(),screening.getRoom());
        assertEquals(screeningDto.startOfScreening(),screening.getStartOfScreening());
    }

    @Test
    void testScreeningShouldReturnScreeningLength() {
        var screeningDto = new ScreeningDto(movie, room, LocalDateTime.now());

        var result = screeningDto.screeningLength();

        assertEquals(movie.getLength(),result);
    }

    @Test
    void testScreeningShouldReturnScreeningEnd() {
        var start = LocalDateTime.now();
        var screeningDto = new ScreeningDto(movie, room, start);

        var result = screeningDto.endOfScreening();

        assertEquals(start.plusMinutes(screeningDto.screeningLength()),result);
    }
}
