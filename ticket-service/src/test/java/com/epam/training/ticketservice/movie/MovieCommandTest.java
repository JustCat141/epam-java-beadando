package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("it")
public class MovieCommandTest {

    @Autowired
    private Shell shell;

    @Autowired
    private MovieRepository movieRepository;

    @SpyBean
    private MovieService movieService;

    @Test
    void testCreateMovie() {
        // Given
        shell.evaluate(() -> "sign in privileged admin admin");
        shell.evaluate(() -> "create movie M G 100");

        // When
        shell.evaluate(() -> "list movies");
        verify(movieService).getMovieList();
        assertEquals(1,movieRepository.findAll().size());
    }
}
