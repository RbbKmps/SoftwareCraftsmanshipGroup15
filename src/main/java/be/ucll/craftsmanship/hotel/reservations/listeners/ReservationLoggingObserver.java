package be.ucll.craftsmanship.hotel.reservations.listeners;

import be.ucll.craftsmanship.hotel.reservations.events.DomainEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationConfirmedEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventObserver;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationPendingEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationRejectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReservationLoggingObserver implements ReservationEventObserver {
    private static final Logger logger = LoggerFactory.getLogger(ReservationLoggingObserver.class);

    @Override
    public void onEventPublished(DomainEvent event) {
        if (event instanceof ReservationPendingEvent) {
            handleReservationPending((ReservationPendingEvent) event);
        } else if (event instanceof ReservationConfirmedEvent) {
            handleReservationConfirmed((ReservationConfirmedEvent) event);
        } else if (event instanceof ReservationRejectedEvent) {
            handleReservationRejected((ReservationRejectedEvent) event);
        }
    }

    private void handleReservationPending(ReservationPendingEvent event) {
        logger.info("Reservation pending validation: ID={}, Room={}, Guest={}, StartDate={}, EndDate={}, EventID={}",
                event.getReservationId(),
                event.getRoomId(),
                event.getGuest(),
                event.getStartDate(),
                event.getEndDate(),
                event.getEventId());
    }

    private void handleReservationConfirmed(ReservationConfirmedEvent event) {
        logger.info("Reservation confirmed: ID={}, Room={}, Guest={}, StartDate={}, EndDate={}, EventID={}",
                event.getReservationId(),
                event.getRoomId(),
                event.getGuest(),
                event.getStartDate(),
                event.getEndDate(),
                event.getEventId());
    }

    private void handleReservationRejected(ReservationRejectedEvent event) {
        logger.warn("Reservation rejected: ID={}, Room={}, Reason={}, EventID={}",
                event.getReservationId(),
                event.getRoomId(),
                event.getReason(),
                event.getEventId());
    }
}
