package com.epam.training.ticketservice.core.movie.persistence;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String title;

    private String genre;

    private Integer length;

    public Movie(String title, String genre, Integer length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public MovieDto asDto() {
        return new MovieDto(title,genre,length);
    }
}
