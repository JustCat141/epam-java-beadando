package com.epam.training.ticketservice.core.room.persistence;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @Column(unique = true)
    private String name;

    private Integer seatRows;

    private Integer seatColumns;

    public RoomDto asDto() {
        return new RoomDto(name,seatRows,seatColumns);
    }
}
