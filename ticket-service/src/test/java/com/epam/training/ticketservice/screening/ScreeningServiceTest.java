package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ScreeningServiceTest {

    @Mock
    private final MovieRepository movieRepository = mock(MovieRepository.class);

    @Mock
    private final RoomRepository roomRepository = mock(RoomRepository.class);

    @Mock
    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);

    @InjectMocks
    private ScreeningService underTest = new ScreeningServiceImpl(movieRepository, roomRepository, screeningRepository);

    @Test
    void testCreateScreeningShouldReturnScreeningDtoWhenValidInput() {
        String movieTitle = "Movie1";
        String roomName = "Room1";
        String startOfScreening = "2021-03-14 16:00";

        when(movieRepository.findByTitle(movieTitle)).thenReturn(Optional.of(new Movie(movieTitle, "Genre", 120)));
        when(roomRepository.findByName(roomName)).thenReturn(Optional.of(new Room(roomName, 5, 10)));

        var result = underTest.createScreening(movieTitle, roomName, startOfScreening);

        assertNotNull(result);
        assertEquals(movieTitle, result.movie().getTitle());
        assertEquals(roomName, result.room().getName());
        assertEquals(LocalDateTime.parse(startOfScreening, DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT)), result.startOfScreening());

        verify(movieRepository).findByTitle(movieTitle);
        verify(roomRepository).findByName(roomName);
    }

    @Test
    void testCreateScreeningShouldReturnNullWhenMovieDoesNotExist() {
        String movieTitle = "Movie1";
        String roomName = "Room1";
        String startOfScreening = "2021-03-14 16:00";

        when(movieRepository.findByTitle(movieTitle)).thenReturn(Optional.empty());

        ScreeningDto result = underTest.createScreening(movieTitle, roomName, startOfScreening);

        assertNull(result);

        verify(movieRepository).findByTitle(movieTitle);
        verify(roomRepository).findByName(any());
    }

    @Test
    void testCreateScreeningShouldReturnNullWhenRoomDoesNotExist() {
        String movieTitle = "Movie1";
        String roomName = "Room1";
        String startOfScreening = "2021-03-14 16:00";

        when(movieRepository.findByTitle(movieTitle)).thenReturn(Optional.of(new Movie(movieTitle, "Genre", 120)));
        when(roomRepository.findByName(roomName)).thenReturn(Optional.empty());

        ScreeningDto result = underTest.createScreening(movieTitle, roomName, startOfScreening);

        assertNull(result);

        verify(movieRepository).findByTitle(movieTitle);
        verify(roomRepository).findByName(roomName);
    }

    @Test
    void testUploadScreeningShouldSaveScreening() {
        ScreeningDto screeningDto = new ScreeningDto(new Movie("Movie1", "Genre", 120),
                new Room("Room1", 5, 10), LocalDateTime.now());

        underTest.uploadScreening(screeningDto);

        verify(screeningRepository).save(any(Screening.class));
    }

    @Test
    void testIsOverlappingShouldReturnTrueWhenScreeningsOverlap() {
        Room room = new Room("Room1", 5, 10);
        Movie movie = new Movie("Movie1", "Genre", 120);

        ScreeningDto first = new ScreeningDto(movie, room, LocalDateTime.parse("2021-03-14 16:00", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT)));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findAllByRoomName(room.getName())).thenReturn(Arrays.asList(first.asEntity()));

        ScreeningDto second = new ScreeningDto(movie, room, LocalDateTime.parse("2021-03-14 17:00", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT)));
        boolean result = underTest.isOverlapping(second, 0);

        assertTrue(result);

        verify(roomRepository).findByName(room.getName());
        verify(screeningRepository).findAllByRoomName(room.getName());
    }

    @Test
    void testIsOverlappingShouldReturnTrueWhenScreeningsOverlapInBreakTime() {
        Room room = new Room("Room1", 5, 10);
        Movie movie = new Movie("Movie1", "Genre", 120);

        ScreeningDto first = new ScreeningDto(movie, room, LocalDateTime.parse("2021-03-14 16:00", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT)));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findAllByRoomName(room.getName())).thenReturn(List.of(first.asEntity()));

        ScreeningDto second = new ScreeningDto(movie, room, LocalDateTime.parse("2021-03-14 18:04", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT)));
        boolean result = underTest.isOverlapping(second, 10);

        assertTrue(result);

        verify(roomRepository).findByName(room.getName());
        verify(screeningRepository).findAllByRoomName(room.getName());
    }

    @Test
    void testIsOverlappingShouldReturnFalseWhenScreeningsNotOverlap() {
        Room room = new Room("Room1", 5, 10);
        Movie movie = new Movie("Movie1", "Genre", 120);

        ScreeningDto first = new ScreeningDto(movie, room, LocalDateTime.parse("2021-03-14 16:00", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT)));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findAllByRoomName(room.getName())).thenReturn(List.of(first.asEntity()));

        ScreeningDto second = new ScreeningDto(movie, room, LocalDateTime.parse("2021-03-14 20:00", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT)));
        boolean result = underTest.isOverlapping(second, 0);

        assertFalse(result);

        verify(roomRepository).findByName(room.getName());
        verify(screeningRepository).findAllByRoomName(room.getName());
    }

    @Test
    void testDeleteScreeningShouldReturnScreeningDtoWhenScreeningDeleted() {
        String movieTitle = "Movie1";
        String roomName = "Room1";
        LocalDateTime startOfScreening = LocalDateTime.parse("2021-03-14 16:00", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT));

        Screening screening = new Screening(new Movie(movieTitle, "Genre", 120),
                new Room(roomName, 5, 10), startOfScreening);
        when(screeningRepository.findByMovieTitleAndRoomNameAndStartOfScreening(movieTitle, roomName, startOfScreening))
                .thenReturn(Optional.of(screening));

        Optional<ScreeningDto> result = underTest.deleteScreening(movieTitle, roomName, startOfScreening);

        assertTrue(result.isPresent());
        assertEquals(screening.asDto(), result.get());

        verify(screeningRepository).findByMovieTitleAndRoomNameAndStartOfScreening(movieTitle, roomName, startOfScreening);
        verify(screeningRepository).delete(screening);
    }

    @Test
    void testDeleteScreeningShouldReturnEmptyWhenScreeningDoesNotExist() {
        String movieTitle = "NonExistingMovie";
        String roomName = "Room1";
        LocalDateTime startOfScreening = LocalDateTime.parse("2023-12-01 14:00", DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT));

        when(screeningRepository.findByMovieTitleAndRoomNameAndStartOfScreening(movieTitle, roomName, startOfScreening))
                .thenReturn(Optional.empty());

        Optional<ScreeningDto> result = underTest.deleteScreening(movieTitle, roomName, startOfScreening);

        assertTrue(result.isEmpty());

        verify(screeningRepository).findByMovieTitleAndRoomNameAndStartOfScreening(movieTitle, roomName, startOfScreening);
        verify(screeningRepository, never()).delete(any());
    }

    @Test
    void testGetScreeningListShouldReturnListOfScreenings() {
        Screening screening1 = new Screening(new Movie("Movie1", "Genre1", 120),
                new Room("Room1", 5, 10), LocalDateTime.now());
        Screening screening2 = new Screening(new Movie("Movie2", "Genre2", 150),
                new Room("Room2", 6, 12), LocalDateTime.now().plusDays(1));

        when(screeningRepository.findAll()).thenReturn(List.of(screening1, screening2));

        List<ScreeningDto> screeningList = underTest.getScreeningList();

        assertEquals(2, screeningList.size());

        verify(screeningRepository).findAll();
    }

    @Test
    void testGetScreeningListShouldReturnEmptyListOfScreeningsWhenScreeningsArentAvailable() {
        when(screeningRepository.findAll()).thenReturn(List.of());

        List<ScreeningDto> screeningList = underTest.getScreeningList();

        assertEquals(0, screeningList.size());

        verify(screeningRepository).findAll();
    }
}