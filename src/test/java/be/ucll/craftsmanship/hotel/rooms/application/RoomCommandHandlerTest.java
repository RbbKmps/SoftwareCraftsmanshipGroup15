package be.ucll.craftsmanship.hotel.rooms.application;

import be.ucll.craftsmanship.hotel.rooms.commands.CreateRoomCommand;
import be.ucll.craftsmanship.hotel.rooms.director.RoomDirector;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.domain.RoomType;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomCommandHandlerTest {

    private RoomRepository roomRepository;
    private RoomDirector roomDirector;
    private RoomCommandHandler roomCommandHandler;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        roomDirector = new RoomDirector();
        roomCommandHandler = new RoomCommandHandler(roomRepository, roomDirector);
    }

    @Test
    void createNormalRoom() {
        // Given
        CreateRoomCommand command = new CreateRoomCommand(102, RoomType.NORMAL);

        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Room room = roomCommandHandler.createRoom(command);

        // Then
        assertNotNull(room);
        assertEquals(102, room.getRoomNumber());
        assertEquals(RoomType.NORMAL, room.getType());
        assertNull(room.getAirConditioning());
        assertNull(room.getWifi());

        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void createLuxuryRoom() {
        // Given
        CreateRoomCommand command = new CreateRoomCommand(101, RoomType.LUXURY);

        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Room room = roomCommandHandler.createRoom(command);

        // Then
        assertNotNull(room);
        assertEquals(101, room.getRoomNumber());
        assertEquals(RoomType.LUXURY, room.getType());
        assertNotNull(room.getAirConditioning());
        assertNotNull(room.getWifi());

        verify(roomRepository).save(any(Room.class));
    }
}