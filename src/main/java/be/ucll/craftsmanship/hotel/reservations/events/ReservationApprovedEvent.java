package be.ucll.craftsmanship.hotel.reservations.events;

public class ReservationApprovedEvent extends DomainEvent {
    private final Long reservationId;
    private final Long roomId;

    public ReservationApprovedEvent(Long reservationId, Long roomId) {
        super();
        this.reservationId = reservationId;
        this.roomId = roomId;
    }

    @Override
    public String getEventType() {
        return "ReservationApprovedEvent";
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getRoomId() {
        return roomId;
    }
}
