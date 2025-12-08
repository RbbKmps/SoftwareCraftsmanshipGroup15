package be.ucll.craftsmanship.hotel.reservations.exceptions;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super(message);
    }

    public RoomNotFoundException(Long roomId) {
        super("Room with ID " + roomId + " not found");
    }
}
