package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommand extends CommandAvailability {

    private final ScreeningService screeningService;

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create screening", value = "Creates a screening")
    public String createScreening(String movieTitle, String roomName, String date) {
        var screeningDto = screeningService.createScreening(movieTitle, roomName, date);
        if (screeningService.isOverlapping(screeningDto, 0)) {
            return "There is an overlapping screening";
        } else if (screeningService.isOverlapping(screeningDto, 10)) {
            return "This would start in the break period after another screening in this room";
        } else {
            screeningService.uploadScreening(screeningDto);
            return "A new screening created in room " + roomName + ", at " + date;
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete screening", value = "Deletes a screening")
    public String deleteScreening(String movieTitle, String roomName, String startOfScreening) {
        var startDate = LocalDateTime.parse(
                startOfScreening,
                DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT));

        return screeningService.deleteScreening(movieTitle, roomName, startDate)
                .map(screeningDto -> screeningDto + " has been deleted!")
                .orElse("There isn't any screening with these data!");
    }

    @ShellMethod(key = "list screenings")
    public String listRooms() {
        var screenings = screeningService.getScreeningList();

        if (screenings.isEmpty()) {
            return "There are no screenings";
        }
        return screenings
                .stream()
                .map(screeningDto -> String.format("%s (%s, %d minutes), screened in room %s, at %s",
                        screeningDto.movie().getTitle(),
                        screeningDto.movie().getGenre(),
                        screeningDto.movie().getLength(),
                        screeningDto.room().getName(),
                        screeningDto.startOfScreening()
                                .format(DateTimeFormatter.ofPattern(ScreeningServiceImpl.DATE_FORMAT))))
                .collect(Collectors.joining("\n"));
    }
}