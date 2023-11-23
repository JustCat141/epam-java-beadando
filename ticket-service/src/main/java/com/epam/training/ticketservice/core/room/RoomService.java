package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Optional<RoomDto> createRoom(RoomDto roomDto);

    Optional<RoomDto> updateRoom(RoomDto roomDto);

    Optional<RoomDto> deleteRoom(String name);

    List<RoomDto> getRoomList();
}
