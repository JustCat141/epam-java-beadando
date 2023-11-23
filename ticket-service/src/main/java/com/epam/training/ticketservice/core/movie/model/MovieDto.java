package com.epam.training.ticketservice.core.movie.model;

import com.epam.training.ticketservice.core.movie.persistence.Movie;

public record MovieDto(String title, String genre, Integer length) {
    public Movie asEntity() {
        return new Movie(title, genre, length);
    }
}
