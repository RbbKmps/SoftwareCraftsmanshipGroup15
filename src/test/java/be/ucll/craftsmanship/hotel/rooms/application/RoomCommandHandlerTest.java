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
        CreateRoomCommand command = new CreateRoomCommand(1, RoomType.NORMAL);

        when(roomRepository.findByRoomNumber(1)).thenReturn(null);
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Room room = roomCommandHandler.createRoom(command);

        // Then
        assertNotNull(room);
        assertEquals(1, room.getRoomNumber());
        assertEquals(RoomType.NORMAL, room.getType());
        assertNull(room.getAirConditioning());
        assertNull(room.getWifi());

        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void createLuxuryRoom() {
        // Given
        CreateRoomCommand command = new CreateRoomCommand(2, RoomType.LUXURY);

        when(roomRepository.findByRoomNumber(2)).thenReturn(null);
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Room room = roomCommandHandler.createRoom(command);

        // Then
        assertNotNull(room);
        assertEquals(2, room.getRoomNumber());
        assertEquals(RoomType.LUXURY, room.getType());
        assertNotNull(room.getAirConditioning());
        assertNotNull(room.getWifi());

        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void createRoomWithDuplicateRoomNumber_shouldThrowException() {
        // Given
        CreateRoomCommand command = new CreateRoomCommand(1, RoomType.NORMAL);
        Room existingRoom = mock(Room.class);

        when(roomRepository.findByRoomNumber(1)).thenReturn(existingRoom);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            roomCommandHandler.createRoom(command);
        });

        assertEquals("Room number 1 already exists", exception.getMessage());
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void deleteExistingRoom() {
        // Given
        int roomNumber = 1;
        Room existingRoom = mock(Room.class);

        when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(existingRoom);

        // When
        roomCommandHandler.deleteRoom(new be.ucll.craftsmanship.hotel.rooms.commands.DeleteRoomCommand(roomNumber));

        // Then
        verify(roomRepository).delete(existingRoom);
    }

    @Test
    void deleteNonExistingRoom_shouldThrowException() {
        // Given
        int roomNumber = 1;

        when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            roomCommandHandler.deleteRoom(new be.ucll.craftsmanship.hotel.rooms.commands.DeleteRoomCommand(roomNumber));
        });

        assertEquals("Room number 1 does not exist", exception.getMessage());
        verify(roomRepository, never()).delete(any(Room.class));
    }
}