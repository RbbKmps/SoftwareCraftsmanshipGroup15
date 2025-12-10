package be.ucll.craftsmanship.hotel.reservations.application;

import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.domain.ReservationStatus;
import be.ucll.craftsmanship.hotel.reservations.infrastructure.ReservationRepository;
import be.ucll.craftsmanship.hotel.reservations.queries.GetAllReservationsQuery;
import be.ucll.craftsmanship.hotel.reservations.queries.GetReservationQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationQueryHandlerTest {
    @Mock
    private ReservationRepository reservationRepository;

    private ReservationQueryHandler queryHandler;
    private Date startDate;
    private Date endDate;
    private Reservation confirmedReservation;
    private Reservation cancelledReservation;
    private Reservation rejectedReservation;

    @BeforeEach
    void setUp() {
        queryHandler = new ReservationQueryHandler(reservationRepository);
        startDate = new Date(System.currentTimeMillis() + 86400000);
        endDate = new Date(System.currentTimeMillis() + 172800000);

        confirmedReservation = new Reservation(1L, "John Doe", startDate, endDate);
        confirmedReservation.approve();

        cancelledReservation = new Reservation(2L, "Jane Doe", startDate, endDate);
        cancelledReservation.cancel();

        rejectedReservation = new Reservation(3L, "Jack Doe", startDate, endDate);
        rejectedReservation.reject();
    }

    @Test
    void getReservation_Found() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(confirmedReservation));

        Reservation result = queryHandler.getReservation(new GetReservationQuery(1L));

        assertNotNull(result);
        assertEquals("John Doe", result.getGuest());
        assertEquals(ReservationStatus.CONFIRMED, result.getStatus());
    }

    @Test
    void getReservation_NotFound() {
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        Reservation result = queryHandler.getReservation(new GetReservationQuery(999L));

        assertNull(result);
    }

    @Test
    void getAllReservations() {
        List<Reservation> allReservations = List.of(confirmedReservation, cancelledReservation, rejectedReservation);
        when(reservationRepository.findAll()).thenReturn(allReservations);

        List<Reservation> result = queryHandler.getAllReservations(new GetAllReservationsQuery());

        assertEquals(3, result.size());
        assertTrue(result.contains(confirmedReservation));
        assertTrue(result.contains(cancelledReservation));
        assertTrue(result.contains(rejectedReservation));
    }

    @Test
    void getActiveReservations() {
        List<Reservation> allReservations = List.of(confirmedReservation, cancelledReservation, rejectedReservation);
        when(reservationRepository.findAll()).thenReturn(allReservations);

        List<Reservation> result = queryHandler.getActiveReservations();

        assertEquals(1, result.size());
        assertEquals(confirmedReservation, result.get(0));
        assertEquals(ReservationStatus.CONFIRMED, result.get(0).getStatus());
    }

    @Test
    void getCancelledReservations() {
        List<Reservation> allReservations = List.of(confirmedReservation, cancelledReservation, rejectedReservation);
        when(reservationRepository.findAll()).thenReturn(allReservations);

        List<Reservation> result = queryHandler.getCancelledReservations();

        assertEquals(1, result.size());
        assertEquals(cancelledReservation, result.get(0));
        assertEquals(ReservationStatus.CANCELLED, result.get(0).getStatus());
    }

    @Test
    void getRejectedReservations() {
        List<Reservation> allReservations = List.of(confirmedReservation, cancelledReservation, rejectedReservation);
        when(reservationRepository.findAll()).thenReturn(allReservations);

        List<Reservation> result = queryHandler.getRejectedReservations();

        assertEquals(1, result.size());
        assertEquals(rejectedReservation, result.get(0));
        assertEquals(ReservationStatus.REJECTED, result.get(0).getStatus());
    }
}