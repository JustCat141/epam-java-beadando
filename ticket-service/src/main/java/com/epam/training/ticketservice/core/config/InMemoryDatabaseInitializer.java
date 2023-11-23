package com.epam.training.ticketservice.core.config;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import com.epam.training.ticketservice.core.user.persistence.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Profile(value = "!prod")
public class InMemoryDatabaseInitializer {
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin","admin", UserRole.ADMIN);
        userRepository.save(admin);

        User test = new User("test","test", UserRole.USER);
        userRepository.save(test);

        Movie m1 = new Movie("Avatar", "drama",120);
        movieRepository.save(m1);
        Movie m2 = new Movie("Avatar 2", "drama",180);
        movieRepository.save(m2);

        Room r1 = new Room("A101", 20,20);
        roomRepository.save(r1);
        Room r2 = new Room("A102", 20,20);
        roomRepository.save(r2);
    }
}
