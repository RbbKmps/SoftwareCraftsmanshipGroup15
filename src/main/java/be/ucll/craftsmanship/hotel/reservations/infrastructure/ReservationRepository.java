package be.ucll.craftsmanship.hotel.reservations.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;

@RestController
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
