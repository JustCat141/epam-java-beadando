package com.epam.training.ticketservice.core.movie.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    @Column(unique = true)
    private String title;

    private String genre;

    private Integer length;

    public Movie(String title, String genre, Integer length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }
}
