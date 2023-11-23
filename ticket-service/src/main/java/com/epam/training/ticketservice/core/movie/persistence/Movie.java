package com.epam.training.ticketservice.core.movie.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    @Column(unique = true)
    private String title;

    private String genre;

    private Integer length;
}
