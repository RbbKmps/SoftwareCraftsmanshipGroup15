package be.ucll.craftsmanship.hotel.reservations.listeners;

import be.ucll.craftsmanship.hotel.reservations.events.DomainEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationConfirmedEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventObserver;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationRejectedEvent;
import org.springframework.stereotype.Component;

@Component
public class ReservationNotificationObserver implements ReservationEventObserver {

    @Override
    public void onEventPublished(DomainEvent event) {
        if (event instanceof ReservationConfirmedEvent) {
            handleReservationConfirmed((ReservationConfirmedEvent) event);
        } else if (event instanceof ReservationRejectedEvent) {
            handleReservationRejected((ReservationRejectedEvent) event);
        }
    }

    private void handleReservationConfirmed(ReservationConfirmedEvent event) {
        String message = String.format(
                "✓ Confirmation: Your reservation has been approved and confirmed. Guest: %s, Room: %d, From: %s, To: %s",
                event.getGuest(),
                event.getRoomId(),
                event.getStartDate(),
                event.getEndDate());
        sendNotification(message);
    }

    private void handleReservationRejected(ReservationRejectedEvent event) {
        String message = String.format(
                "✗ Rejection: Your reservation was rejected. Reason: %s",
                event.getReason());
        sendNotification(message);
    }

    private void sendNotification(String message) {
        System.out.println("\n========================================");
        System.out.println(message);
        System.out.println("========================================\n");
    }
}
