package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Optional<MovieDto> createMovie(MovieDto movieDto) {
        var existingMovie = movieRepository.findById(movieDto.title());

        if(existingMovie.isPresent()) {
            return Optional.empty();
        }

        Movie movie = new Movie(
                movieDto.title(),
                movieDto.genre(),
                movieDto.length()
        );
        movieRepository.save(movie);

        return Optional.of(movieDto);
    }

    @Override
    public Optional<MovieDto> updateMovie(MovieDto movieDto) {
        Optional<Movie> movieToUpdate = movieRepository.findById(movieDto.title());

        if (movieToUpdate.isEmpty()) {
            return Optional.empty();
        }

        var movie = movieToUpdate.get();
        movie.setGenre(movieDto.genre());
        movie.setLength(movieDto.length());

        movieRepository.save(movie);
        return Optional.of(movieDto);
    }

    @Override
    public Optional<MovieDto> deleteMovie(String title) {
        var movie = movieRepository.findById(title);
        if(movie.isEmpty()) {
            return Optional.empty();
        }

        movieRepository.deleteById(title);
        return Optional.of(new MovieDto(
                movie.get().getTitle(),
                movie.get().getGenre(),
                movie.get().getLength()));
    }

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getGenre(), movie.getLength()))
                .toList();
    }
}
