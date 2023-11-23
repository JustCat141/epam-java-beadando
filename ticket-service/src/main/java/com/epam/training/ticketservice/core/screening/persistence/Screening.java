package com.epam.training.ticketservice.core.screening.persistence;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDateTime startOfScreening;

    public Screening(Movie movie, Room room, LocalDateTime startOfScreening) {
        this.movie = movie;
        this.room = room;
        this.startOfScreening = startOfScreening;
    }

    public ScreeningDto asDto() {
        return new ScreeningDto(movie, room, startOfScreening);
    }
}
