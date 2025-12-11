package be.ucll.craftsmanship.hotel.reservations.events;

import java.util.Date;

public class ReservationConfirmedEvent extends DomainEvent {
    private final Long reservationId;
    private final Long roomId;
    private final String guest;
    private final Date startDate;
    private final Date endDate;

    public ReservationConfirmedEvent(Long reservationId, Long roomId, String guest, Date startDate, Date endDate) {
        super();
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getEventType() {
        return "ReservationConfirmedEvent";
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getGuest() {
        return guest;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
