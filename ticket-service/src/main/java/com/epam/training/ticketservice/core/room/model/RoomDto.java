package com.epam.training.ticketservice.core.room.model;

import com.epam.training.ticketservice.core.room.persistence.Room;

public record RoomDto(String name, Integer seatRows, Integer seatColumns) {

    public Integer seatCount() {
        return seatRows * seatColumns;
    }

    public Room asEntity() {
        return new Room(name,seatRows,seatColumns);
    }
}
