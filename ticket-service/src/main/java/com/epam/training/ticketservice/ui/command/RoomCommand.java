package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class RoomCommand extends CommandAvailability{
    private RoomService roomService;

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create room", value = "Creates a room")
    public String createRoom(String name, Integer seatRows, Integer seatColumns) {
        var room = new RoomDto(name, seatRows, seatColumns);
        return roomService.createRoom(room)
                .map(roomDto -> roomDto.name() +" has been created successfully")
                .orElse("Room already exists with this name!");
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update room")
    public String updateRoom(String name, Integer seatRows, Integer seatColumns) {
        var room = new RoomDto(name, seatRows, seatColumns);
        return roomService.updateRoom(room)
                .map(movieDto -> movieDto.name() + " has been updated!")
                .orElse("There are no rooms with this name!");
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete room")
    public String deleteRoom(String name) {
        return roomService.deleteRoom(name)
                .map(roomDto -> roomDto.name() + " has been deleted successfully!")
                .orElse("There are no movies with this name!");
    }

    @ShellMethod(key = "list rooms")
    public String listRooms() {
        var rooms = roomService.getRoomList();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return rooms
                .stream()
                .map(roomDto -> String.format("Room %s with %d seats, %d rows and %d columns)",
                        roomDto.name(),
                        roomDto.seatCount(),
                        roomDto.seatRows(),
                        roomDto.seatColumns()))
                .collect(Collectors.joining("\n"));

    }
}