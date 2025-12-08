package be.ucll.craftsmanship.hotel.reservations.events;

/**
 * Observer interface for reservation events.
 * Implementations will be notified when reservation events are published.
 */
public interface ReservationEventObserver {
    void onEventPublished(DomainEvent event);
}
