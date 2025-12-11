package be.ucll.craftsmanship.hotel.reservations.application;

import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.domain.ReservationStatus;
import be.ucll.craftsmanship.hotel.reservations.infrastructure.ReservationRepository;
import be.ucll.craftsmanship.hotel.reservations.queries.GetAllReservationsQuery;
import be.ucll.craftsmanship.hotel.reservations.queries.GetReservationQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationQueryHandler {
    private final ReservationRepository reservationRepository;

    public ReservationQueryHandler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation getReservation(GetReservationQuery query) {
        return reservationRepository.findById(query.reservationId())
                .orElse(null);
    }

    public List<Reservation> getAllReservations(GetAllReservationsQuery query) {
        return reservationRepository.findAll();
    }

    public List<Reservation> getActiveReservations() {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .toList();
    }

    public List<Reservation> getCancelledReservations() {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStatus() == ReservationStatus.CANCELLED)
                .toList();
    }

    public List<Reservation> getRejectedReservations() {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStatus() == ReservationStatus.REJECTED)
                .toList();
    }
}
