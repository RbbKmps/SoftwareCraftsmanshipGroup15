package be.ucll.craftsmanship.hotel.reservations.exceptions;

public class ReservationRejectedException extends RuntimeException {
    public ReservationRejectedException(String reason) {
        super("Reservation rejected: " + reason);
    }
}
