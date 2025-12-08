package be.ucll.craftsmanship.hotel.reservations.listeners;

import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.events.DomainEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationApprovedEvent;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventObserver;
import be.ucll.craftsmanship.hotel.reservations.infrastructure.ReservationRepository;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomUpdaterObserver implements ReservationEventObserver {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomUpdaterObserver(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void onEventPublished(DomainEvent event) {
        if (!(event instanceof ReservationApprovedEvent)) {
            return;
        }

        ReservationApprovedEvent approved = (ReservationApprovedEvent) event;

        Reservation reservation = reservationRepository.findById(approved.getReservationId())
                .orElse(null);

        if (reservation == null) {
            return;
        }

        Room room = roomRepository.findById(approved.getRoomId())
                .orElse(null);

        if (room == null) {
            return;
        }

        room.addReservation(
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate()
        );
        roomRepository.save(room);
    }
}
