package be.ucll.craftsmanship.hotel.reservations.listeners;

import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.domain.ReservationStatus;
import be.ucll.craftsmanship.hotel.reservations.events.DomainEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationApprovedEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventObserver;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationPendingEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationRejectedEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventPublisher;
import be.ucll.craftsmanship.hotel.reservations.infrastructure.ReservationRepository;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RoomReservationObserver implements ReservationEventObserver {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationEventPublisher eventPublisher;

    public RoomReservationObserver(RoomRepository roomRepository,
                                  ReservationRepository reservationRepository,
                                  ReservationEventPublisher eventPublisher) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onEventPublished(DomainEvent event) {
        if (!(event instanceof ReservationPendingEvent)) {
            return;
        }

        ReservationPendingEvent pending = (ReservationPendingEvent) event;
        
        if (!roomRepository.existsById(pending.getRoomId())) {
            eventPublisher.publish(
                    new ReservationRejectedEvent(
                            pending.getReservationId(),
                            pending.getRoomId(),
                            "Room not found"
                    )
            );
            return;
        }

        boolean hasConflict = hasDateConflict(
                pending.getRoomId(),
                pending.getReservationId(),
                pending.getStartDate(),
                pending.getEndDate()
        );

        if (hasConflict) {
            eventPublisher.publish(
                    new ReservationRejectedEvent(
                            pending.getReservationId(),
                            pending.getRoomId(),
                            "Date conflict with existing reservation"
                    )
            );
        } else {
            eventPublisher.publish(
                    new ReservationApprovedEvent(
                            pending.getReservationId(),
                            pending.getRoomId()
                    )
            );
        }
    }

    private boolean hasDateConflict(Long roomId, Long reservationIdToIgnore, Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }

        List<Reservation> confirmedReservations = reservationRepository.findAll().stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .filter(r -> !r.getId().equals(reservationIdToIgnore))
                .toList();

        for (Reservation existing : confirmedReservations) {
            if (dateRangesConflict(startDate, endDate, existing.getStartDate(), existing.getEndDate())) {
                return true;
            }
        }

        return false;
    }

    private boolean dateRangesConflict(Date start1, Date end1, Date start2, Date end2) {
        return !end1.before(start2) && !end2.before(start1);
    }
}
