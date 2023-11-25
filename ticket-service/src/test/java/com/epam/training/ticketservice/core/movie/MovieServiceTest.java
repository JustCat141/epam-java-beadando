package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.MovieServiceImpl;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MovieServiceTest {
    @Mock
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieService underTest = new MovieServiceImpl(movieRepository);


    @Test
    void testCreateMovieShouldReturnMovieWhenMovieCreated() {
        // Given
        var movieDto = new MovieDto("title","genre",120);
        when(movieRepository.findByTitle("title")).thenReturn(Optional.empty());
        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie("title", "genre", 120));

        // When
        Optional<MovieDto> result = underTest.createMovie(movieDto);

        // Then
        assertTrue(result.isPresent());
        assertEquals(movieDto, result.get());
        verify(movieRepository, times(1)).findByTitle("title");
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testCreateMovieShouldReturnEmptyWhenMovieAlreadyExists() {
        // Given
        MovieDto movieDto = new MovieDto("Title", "Genre", 120);
        when(movieRepository.findByTitle("Title")).thenReturn(Optional.of(new Movie("Title", "Genre", 120)));

        // When
        Optional<MovieDto> result = underTest.createMovie(movieDto);

        // Then
        assertTrue(result.isEmpty());
        verify(movieRepository, times(1)).findByTitle("Title");
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void testUpdateMovieShouldReturnMovieWhenMovieIsPresent() {
        MovieDto movieDto = new MovieDto("Title", "Genre", 120);
        when(movieRepository.findByTitle("Title")).thenReturn(Optional.of(new Movie("Title", "OldGenre", 90)));
        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie("Title", "Genre", 120));

        Optional<MovieDto> result = underTest.updateMovie(movieDto);

        assertTrue(result.isPresent());
        assertEquals(movieDto, result.get());
        verify(movieRepository, times(1)).findByTitle("Title");
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovieShouldReturnEmptyWhenMovieDoesNotExist() {
        MovieDto movieDto = new MovieDto("Title", "Genre", 120);
        when(movieRepository.findByTitle("Title")).thenReturn(Optional.empty());

        Optional<MovieDto> result = underTest.updateMovie(movieDto);

        assertTrue(result.isEmpty());
        verify(movieRepository, times(1)).findByTitle("Title");
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void testDeleteMovieShouldReturnMovieWhenMovieDeleted() {
        when(movieRepository.findByTitle("Title")).thenReturn(Optional.of(new Movie("Title", "Genre", 120)));

        Optional<MovieDto> result = underTest.deleteMovie("Title");

        assertTrue(result.isPresent());
        verify(movieRepository, times(1)).findByTitle("Title");
    }

    @Test
    void testDeleteMovieShouldReturnEmptyWhenMovieDoesNotExist() {
        when(movieRepository.findByTitle("Title")).thenReturn(Optional.empty());

        Optional<MovieDto> result = underTest.deleteMovie("Title");

        assertTrue(result.isEmpty());
        verify(movieRepository, times(1)).findByTitle("Title");
        verify(movieRepository, never()).deleteById(anyInt());
    }

    @Test
    void testGetMovieListShouldReturnListOfMoviesWhenMoviesArePresent() {
        when(movieRepository.findAll()).thenReturn(Arrays.asList(
                new Movie("Title1", "Genre1", 120),
                new Movie("Title2", "Genre2", 150)
        ));

        List<MovieDto> movieList = underTest.getMovieList();

        assertEquals(2, movieList.size());

        verify(movieRepository, times(1)).findAll();
    }
}
