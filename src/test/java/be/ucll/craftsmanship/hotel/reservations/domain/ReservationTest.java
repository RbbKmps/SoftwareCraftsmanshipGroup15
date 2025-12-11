package be.ucll.craftsmanship.hotel.reservations.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private Reservation reservation;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    void setUp() {
        startDate = new Date(System.currentTimeMillis() + 86400000); // tomorrow
        endDate = new Date(System.currentTimeMillis() + 172800000); // day after tomorrow
        reservation = new Reservation(1L, "John Doe", startDate, endDate);
    }

    @Test
    void getId() {
        assertNull(reservation.getId());
    }

    @Test
    void getRoomId() {
        assertEquals(1L, reservation.getRoomId());
    }

    @Test
    void getGuest() {
        assertEquals("John Doe", reservation.getGuest());
    }

    @Test
    void getStartDate() {
        assertEquals(startDate, reservation.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals(endDate, reservation.getEndDate());
    }

    @Test
    void getStatus() {
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
    }

    @Test
    void setGuest() {
        reservation.setGuest("Jane Doe");
        assertEquals("Jane Doe", reservation.getGuest());
    }

    @Test
    void setStartDate() {
        Date newStartDate = new Date(System.currentTimeMillis() + 259200000); // in 3 days
        reservation.setStartDate(newStartDate);
        assertEquals(newStartDate, reservation.getStartDate());
    }

    @Test
    void setEndDate() {
        Date newEndDate = new Date(System.currentTimeMillis() + 345600000); // in 4 days
        reservation.setEndDate(newEndDate);
        assertEquals(newEndDate, reservation.getEndDate());
    }

    @Test
    void approve() {
        reservation.approve();
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }

    @Test
    void reject() {
        reservation.reject();
        assertEquals(ReservationStatus.REJECTED, reservation.getStatus());
    }

    @Test
    void cancel() {
        reservation.cancel();
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }

    @Test
    void isPending() {
        assertTrue(reservation.isPending());
        reservation.approve();
        assertFalse(reservation.isPending());
    }

    @Test
    void isConfirmed() {
        assertFalse(reservation.isConfirmed());
        reservation.approve();
        assertTrue(reservation.isConfirmed());
    }

    @Test
    void isRejected() {
        assertFalse(reservation.isRejected());
        reservation.reject();
        assertTrue(reservation.isRejected());
    }

    @Test
    void isCancelled() {
        assertFalse(reservation.isCancelled());
        reservation.cancel();
        assertTrue(reservation.isCancelled());
    }
}