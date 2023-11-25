package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {

    @Test
    void testMovieShouldReturnDto() {
        var movie = new Movie("asd","asd",10);

        var movieDto = movie.asDto();

        assertEquals(movieDto.title(),movie.getTitle());
        assertEquals(movieDto.genre(),movie.getGenre());
        assertEquals(movieDto.length(),movie.getLength());
    }

    @Test
    void testMovieShouldReturnEntity() {
        var movieDto = new MovieDto("asd","asd",10);

        var movie = movieDto.asEntity();

        assertEquals(movieDto.title(),movie.getTitle());
        assertEquals(movieDto.genre(),movie.getGenre());
        assertEquals(movieDto.length(),movie.getLength());
    }
}
