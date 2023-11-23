package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {

    private final MovieService movieService;

    @ShellMethod(key = "create movie", value = "Creates movie")
    public String createMovie(String title, String genre, Integer length) {
        var movie = new MovieDto(title, genre, length);
        return movieService.createMovie(movie)
                .map(movieDto -> movieDto.title() + " has been created!")
                .orElse("Movie already exists with this title!");
    }

    @ShellMethod(key = "update movie", value = "Updates a movie")
    public String updateMovie(String title, String genre, Integer length) {
        var movie = new MovieDto(title, genre, length);
        return movieService.updateMovie(movie)
                .map(movieDto -> movieDto.title() + " has been updated!")
                .orElse("There are no movies with this title!");
    }

    @ShellMethod(key = "delete movie", value = "Deletes a movie")
    public String deleteMovie(String title) {
        return movieService.deleteMovie(title)
                .map(movieDto -> movieDto.title() + " was deleted successfully!")
                .orElse("There are no movies with this title!");
    }

    @ShellMethod(key = "list movies", value = "Lists movies")
    public String listMovies() {
        var movies = movieService.getMovieList();

        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movies
                .stream()
                .map(movieDto -> String.format("%s (%s, %d minutes)",
                        movieDto.title(),
                        movieDto.genre(),
                        movieDto.length()))
                .collect(Collectors.joining("\n"));

    }
}