package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest {

    @Test
    void testRoomShouldReturnDto() {
        var room = new Room("asd",10,20);

        var roomDto = room.asDto();

        assertEquals(roomDto.name(),room.getName());
        assertEquals(roomDto.seatColumns(),room.getSeatColumns());
        assertEquals(roomDto.seatRows(),room.getSeatRows());
    }

    @Test
    void testRoomShouldReturnEntity() {
        var roomDto = new RoomDto("asd",10,20);

        var room = roomDto.asEntity();

        assertEquals(roomDto.name(),room.getName());
        assertEquals(roomDto.seatColumns(),room.getSeatColumns());
        assertEquals(roomDto.seatRows(),room.getSeatRows());
    }

    @Test
    void testRoomShouldReturnSeatsCount() {
        var roomDto = new RoomDto("asd",10,20);
        int expected = 200;

        var got = roomDto.seatCount();

        assertEquals(expected,got);
    }
}
