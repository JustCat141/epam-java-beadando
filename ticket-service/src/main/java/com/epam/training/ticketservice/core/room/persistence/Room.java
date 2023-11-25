package com.epam.training.ticketservice.core.room.persistence;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    private Integer seatRows;

    private Integer seatColumns;

    public RoomDto asDto() {
        return new RoomDto(name,seatRows,seatColumns);
    }

    public Room(String name, Integer seatRows, Integer seatColumns) {
        this.name = name;
        this.seatRows = seatRows;
        this.seatColumns = seatColumns;
    }
}
