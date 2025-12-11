package be.ucll.craftsmanship.hotel.reservations.events;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ReservationEventPublisher {
    private final List<ReservationEventObserver> observers = new ArrayList<>();

    public void subscribe(ReservationEventObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(ReservationEventObserver observer) {
        observers.remove(observer);
    }

    public void publish(DomainEvent event) {
        for (ReservationEventObserver observer : observers) {
            observer.onEventPublished(event);
        }
    }

    public List<ReservationEventObserver> getObservers() {
        return new ArrayList<>(observers);
    }
}
