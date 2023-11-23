package com.epam.training.ticketservice.core.room.model;

public record RoomDto(String name, Integer seatRows, Integer seatColumns) {

    public Integer seatCount() {
        return seatRows * seatColumns;
    }
}
