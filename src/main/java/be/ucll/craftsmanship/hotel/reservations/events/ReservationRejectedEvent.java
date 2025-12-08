package be.ucll.craftsmanship.hotel.reservations.events;

public class ReservationRejectedEvent extends DomainEvent {
    private final Long reservationId;
    private final Long roomId;
    private final String reason;

    public ReservationRejectedEvent(Long reservationId, Long roomId, String reason) {
        super();
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.reason = reason;
    }

    @Override
    public String getEventType() {
        return "ReservationRejectedEvent";
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getReason() {
        return reason;
    }
}
