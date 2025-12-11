package be.ucll.craftsmanship.hotel.reservations.exceptions;

public class ReservationTimeoutException extends RuntimeException {
    public ReservationTimeoutException(String message) {
        super(message);
    }
}
