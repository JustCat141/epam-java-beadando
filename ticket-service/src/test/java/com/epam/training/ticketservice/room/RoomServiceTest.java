package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class RoomServiceTest {
    @Mock
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomService underTest = new RoomServiceImpl(roomRepository);

    @Test
    void testCreateRoomShouldReturnRoomWhenRoomCreated() {
        RoomDto roomDto = new RoomDto("Room1", 5, 10);
        when(roomRepository.findByName("Room1")).thenReturn(Optional.empty());
        when(roomRepository.save(any(Room.class))).thenReturn(new Room("Room1", 5, 10));

        Optional<RoomDto> result = underTest.createRoom(roomDto);

        assertTrue(result.isPresent());
        assertEquals(roomDto, result.get());

        verify(roomRepository, times(1)).findByName("Room1");
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testCreateRoomShouldReturnEmptyWhenRoomAlreadyExists() {
        RoomDto roomDto = new RoomDto("Room1", 5, 10);
        when(roomRepository.findByName("Room1")).thenReturn(Optional.of(new Room("Room1", 5, 10)));

        Optional<RoomDto> result = underTest.createRoom(roomDto);

        assertTrue(result.isEmpty());

        verify(roomRepository, times(1)).findByName("Room1");
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testUpdateRoomShouldReturnRoomWhenRoomUpdated() {
        RoomDto roomDto = new RoomDto("Room1", 8, 12);
        when(roomRepository.findByName("Room1")).thenReturn(Optional.of(new Room("Room1", 5, 10)));
        when(roomRepository.save(any(Room.class))).thenReturn(new Room("Room1", 8, 12));

        Optional<RoomDto> result = underTest.updateRoom(roomDto);

        assertTrue(result.isPresent());
        assertEquals(roomDto, result.get());

        verify(roomRepository, times(1)).findByName("Room1");
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testUpdateRoomShouldReturnEmptyWhenRoomDoesNotExist() {
        RoomDto roomDto = new RoomDto("Room1", 8, 12);
        when(roomRepository.findByName("Room1")).thenReturn(Optional.empty());

        Optional<RoomDto> result = underTest.updateRoom(roomDto);

        assertTrue(result.isEmpty());

        verify(roomRepository, times(1)).findByName("Room1");
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testDeleteRoomShouldReturnRoomWhenRoomDeleted() {
        String roomName = "Room1";
        when(roomRepository.findByName(roomName)).thenReturn(Optional.of(new Room("Room1", 5, 10)));

        Optional<RoomDto> result = underTest.deleteRoom(roomName);

        assertTrue(result.isPresent());

        verify(roomRepository, times(1)).findByName(roomName);
        verify(roomRepository, times(1)).deleteById(any());
    }

    @Test
    void testDeleteRoomShouldReturnEmptyWhenRoomDoesNotExist() {
        String roomName = "Room1";
        when(roomRepository.findByName(roomName)).thenReturn(Optional.empty());

        Optional<RoomDto> result = underTest.deleteRoom(roomName);

        assertTrue(result.isEmpty());

        verify(roomRepository, times(1)).findByName(roomName);
        verify(roomRepository, never()).deleteById(any());
    }

    @Test
    void testGetRoomListShouldReturnListOfRooms() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(
                new Room("Room1", 5, 10),
                new Room("Room2", 6, 8)
        ));

        List<RoomDto> roomList = underTest.getRoomList();

        assertEquals(2, roomList.size());

        verify(roomRepository, times(1)).findAll();
    }
}
