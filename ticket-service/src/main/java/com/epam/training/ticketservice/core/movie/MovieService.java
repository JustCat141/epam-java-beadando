package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Optional<MovieDto> createMovie(MovieDto movieDto);

    Optional<MovieDto> updateMovie(MovieDto movieDto);

    Optional<MovieDto> deleteMovie(String title);

    List<MovieDto> getMovieList();
}
