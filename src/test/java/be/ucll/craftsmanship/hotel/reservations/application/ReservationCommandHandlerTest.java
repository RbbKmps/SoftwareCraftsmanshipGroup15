package be.ucll.craftsmanship.hotel.reservations.application;

import be.ucll.craftsmanship.hotel.reservations.commands.CancelReservationCommand;
import be.ucll.craftsmanship.hotel.reservations.commands.CreateReservationCommand;
import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.domain.ReservationStatus;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventPublisher;
import be.ucll.craftsmanship.hotel.reservations.exceptions.RoomNotFoundException;
import be.ucll.craftsmanship.hotel.reservations.exceptions.ReservationRejectedException;
import be.ucll.craftsmanship.hotel.reservations.infrastructure.ReservationRepository;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationCommandHandlerTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ReservationEventPublisher eventPublisher;

    private ReservationCommandHandler commandHandler;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    void setUp() {
        commandHandler = new ReservationCommandHandler(reservationRepository, roomRepository, eventPublisher);
        startDate = new Date(System.currentTimeMillis() + 86400000);
        endDate = new Date(System.currentTimeMillis() + 172800000);
    }

    @Test
    void createReservation_Success() {
        CreateReservationCommand command = new CreateReservationCommand(1L, "John Doe", startDate, endDate);
        Reservation reservation = new Reservation(1L, "John Doe", startDate, endDate);
        reservation.approve();

        when(roomRepository.existsById(1L)).thenReturn(true);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationRepository.findById(any())).thenReturn(Optional.of(reservation));

        Reservation result = commandHandler.createReservation(command);

        assertNotNull(result);
        assertEquals(ReservationStatus.CONFIRMED, result.getStatus());
        verify(reservationRepository).save(any(Reservation.class));
        verify(eventPublisher).publish(any());
    }

    @Test
    void createReservation_RoomNotFound() {
        CreateReservationCommand command = new CreateReservationCommand(999L, "John Doe", startDate, endDate);

        when(roomRepository.existsById(999L)).thenReturn(false);

        assertThrows(RoomNotFoundException.class, () -> commandHandler.createReservation(command));
    }

    @Test
    void createReservation_InvalidGuest() {
        CreateReservationCommand command = new CreateReservationCommand(1L, "", startDate, endDate);

        assertThrows(IllegalArgumentException.class, () -> commandHandler.createReservation(command));
    }

    @Test
    void createReservation_InvalidDates() {
        CreateReservationCommand command = new CreateReservationCommand(1L, "John Doe", endDate, startDate);

        assertThrows(IllegalArgumentException.class, () -> commandHandler.createReservation(command));
    }

    @Test
    void createReservation_Rejected() {
        CreateReservationCommand command = new CreateReservationCommand(1L, "John Doe", startDate, endDate);
        Reservation reservation = new Reservation(1L, "John Doe", startDate, endDate);
        reservation.reject();

        when(roomRepository.existsById(1L)).thenReturn(true);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationRepository.findById(any())).thenReturn(Optional.of(reservation));

        assertThrows(ReservationRejectedException.class, () -> commandHandler.createReservation(command));
    }

    @Test
    void cancelReservation_Success() {
        Reservation reservation = new Reservation(1L, "John Doe", startDate, endDate);
        reservation.approve();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        commandHandler.cancelReservation(new CancelReservationCommand(1L));

        assertTrue(reservation.isCancelled());
        verify(reservationRepository).save(reservation);
    }

    @Test
    void cancelReservation_NotFound() {
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> commandHandler.cancelReservation(new CancelReservationCommand(999L)));
    }

    @Test
    void cancelReservation_AlreadyCancelled() {
        Reservation reservation = new Reservation(1L, "John Doe", startDate, endDate);
        reservation.cancel();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThrows(IllegalStateException.class, () -> commandHandler.cancelReservation(new CancelReservationCommand(1L)));
    }
}