package be.ucll.craftsmanship.hotel.rooms.application;

import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.domain.RoomType;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import be.ucll.craftsmanship.hotel.rooms.queries.GetRoomQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomQueryHandlerTest {

    private RoomRepository roomRepository;
    private RoomQueryHandler roomQueryHandler;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        roomQueryHandler = new RoomQueryHandler(roomRepository);
    }

    @Test
    void findAll() {
        // Given
        Room room1 = new Room(1, RoomType.NORMAL, List.of(), null, null);
        Room room2 = new Room(2, RoomType.NORMAL, List.of(), null, null);
        List<Room> rooms = List.of(room1, room2);

        when(roomRepository.findAll()).thenReturn(rooms);

        // When
        List<Room> result = roomQueryHandler.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRoomNumber());
        assertEquals(2, result.get(1).getRoomNumber());
        verify(roomRepository).findAll();
    }

    @Test
    void getRoomByRoomNumber() {
        // Given
        Room room = new Room(3, RoomType.NORMAL, List.of(), null, null);
        GetRoomQuery query = new GetRoomQuery(3);

        when(roomRepository.findByRoomNumber(3)).thenReturn(room);

        // When
        Room result = roomQueryHandler.getRoomByRoomNumber(query);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getRoomNumber());
        assertEquals(RoomType.NORMAL, result.getType());
        verify(roomRepository).findByRoomNumber(3);
    }

    @Test
    void getRoomByRoomNumberNotFound() {
        // Given
        GetRoomQuery query = new GetRoomQuery(999);

        when(roomRepository.findByRoomNumber(999)).thenReturn(null);

        // When
        Room result = roomQueryHandler.getRoomByRoomNumber(query);

        // Then
        assertNull(result);
        verify(roomRepository).findByRoomNumber(999);
    }
}