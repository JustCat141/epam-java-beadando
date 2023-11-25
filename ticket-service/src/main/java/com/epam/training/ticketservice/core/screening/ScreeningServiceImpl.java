package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    @Override
    public ScreeningDto createScreening(String movieTitle, String roomName, String startOfScreening) {
        var movie = movieRepository.findByTitle(movieTitle);
        var room = roomRepository.findByName(roomName);
        var start = LocalDateTime.parse(startOfScreening, DateTimeFormatter.ofPattern(DATE_FORMAT));

        if (movie.isEmpty() || room.isEmpty()) {
            return null;
        }

        return new ScreeningDto(movie.get(), room.get(), start);
    }

    @Override
    public void uploadScreening(ScreeningDto screeningDto) {
        var screening = screeningDto.asEntity();
        screeningRepository.save(screening);
    }

    public boolean isOverlapping(ScreeningDto screeningDto, int additionalTime) {
        var room = roomRepository.findByName(screeningDto.room().getName());
        var screeningStart = screeningDto.startOfScreening();
        var screeningEnd = screeningDto.endOfScreening().plusMinutes(additionalTime);

        var screeningsInRoom = screeningRepository.findAllByRoomName(room.get().getName());
        for (var other : screeningsInRoom) {

            var otherStart = other.getStartOfScreening();
            var otherEnd = other.getStartOfScreening().plusMinutes(other.getMovie().getLength() + additionalTime);

            if (screeningStart.isBefore(otherEnd) && screeningEnd.isAfter(otherStart)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<ScreeningDto> deleteScreening(
            String movieTitle,
            String roomName,
            LocalDateTime startOfScreening) {
        var screening = screeningRepository
                .findByMovieTitleAndRoomNameAndStartOfScreening(movieTitle, roomName, startOfScreening);

        if (screening.isPresent()) {
            screening.ifPresent(screeningRepository::delete);
            return Optional.of(screening.get().asDto());
        }
        return Optional.empty();
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository.findAll()
                .stream()
                .map(Screening::asDto)
                .toList();
    }
}