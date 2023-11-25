package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Optional<RoomDto> createRoom(RoomDto roomDto) {
        var existingRoom = roomRepository.findByName(roomDto.name());

        if (existingRoom.isPresent()) {
            return Optional.empty();
        }

        Room room = new Room(
                roomDto.name(),
                roomDto.seatRows(),
                roomDto.seatColumns()
        );
        roomRepository.save(room);

        return Optional.of(roomDto);
    }

    @Override
    public Optional<RoomDto> updateRoom(RoomDto roomDto) {
        Optional<Room> roomToUpdate = roomRepository.findByName(roomDto.name());

        if (roomToUpdate.isEmpty()) {
            return Optional.empty();
        }

        var room = roomToUpdate.get();
        room.setSeatRows(roomDto.seatRows());
        room.setSeatColumns(roomDto.seatColumns());

        roomRepository.save(room);
        return Optional.of(roomDto);
    }

    @Override
    public Optional<RoomDto> deleteRoom(String name) {
        var room = roomRepository.findByName(name);
        if (room.isEmpty()) {
            return Optional.empty();
        }

        roomRepository.deleteById(room.get().getId());
        return Optional.of(room.get().asDto());
    }

    @Override
    public List<RoomDto> getRoomList() {
        return roomRepository.findAll()
                .stream()
                .map(Room::asDto)
                .toList();
    }
}