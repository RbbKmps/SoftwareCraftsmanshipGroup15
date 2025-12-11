package be.ucll.craftsmanship.hotel.reservations.listeners;

import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.events.DomainEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationApprovedEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationConfirmedEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventObserver;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventPublisher;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationRejectedEvent;
import be.ucll.craftsmanship.hotel.reservations.infrastructure.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
public class ReservationApprovalObserver implements ReservationEventObserver {
    private final ReservationRepository reservationRepository;
    private final ReservationEventPublisher eventPublisher;

    public ReservationApprovalObserver(ReservationRepository reservationRepository, ReservationEventPublisher eventPublisher) {
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onEventPublished(DomainEvent event) {
        if (event instanceof ReservationApprovedEvent) {
            handleApproval((ReservationApprovedEvent) event);
        } else if (event instanceof ReservationRejectedEvent) {
            handleRejection((ReservationRejectedEvent) event);
        }
    }

    private void handleApproval(ReservationApprovedEvent event) {
        Reservation reservation = reservationRepository.findById(event.getReservationId())
                .orElse(null);

        if (reservation == null) {
            return;
        }

        reservation.approve();
        reservationRepository.save(reservation);

        eventPublisher.publish(
                new ReservationConfirmedEvent(
                        reservation.getId(),
                        reservation.getRoomId(),
                        reservation.getGuest(),
                        reservation.getStartDate(),
                        reservation.getEndDate()
                )
        );
    }

    private void handleRejection(ReservationRejectedEvent event) {
        Reservation reservation = reservationRepository.findById(event.getReservationId())
                .orElse(null);

        if (reservation == null) {
            return;
        }

        reservation.reject();
        reservationRepository.save(reservation);
    }
}
