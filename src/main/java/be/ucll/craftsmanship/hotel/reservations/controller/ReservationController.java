package be.ucll.craftsmanship.hotel.reservations.controller;

import be.ucll.craftsmanship.hotel.reservations.application.ReservationCommandHandler;
import be.ucll.craftsmanship.hotel.reservations.application.ReservationQueryHandler;
import be.ucll.craftsmanship.hotel.reservations.commands.CancelReservationCommand;
import be.ucll.craftsmanship.hotel.reservations.commands.CreateReservationCommand;
import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.exceptions.RoomNotFoundException;
import be.ucll.craftsmanship.hotel.reservations.exceptions.ReservationRejectedException;
import be.ucll.craftsmanship.hotel.reservations.exceptions.ReservationTimeoutException;
import be.ucll.craftsmanship.hotel.reservations.queries.GetAllReservationsQuery;
import be.ucll.craftsmanship.hotel.reservations.queries.GetReservationQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationCommandHandler commandHandler;
    private final ReservationQueryHandler queryHandler;

    public ReservationController(ReservationCommandHandler commandHandler, ReservationQueryHandler queryHandler) {
        this.commandHandler = commandHandler;
        this.queryHandler = queryHandler;
    }

    // COMMANDS
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationCommand command) {
        try {
            Reservation reservation = commandHandler.createReservation(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (RoomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ReservationRejectedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ReservationTimeoutException e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        try {
            commandHandler.cancelReservation(new CancelReservationCommand(id));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // QUERIES
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = queryHandler.getAllReservations(new GetAllReservationsQuery());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = queryHandler.getReservation(new GetReservationQuery(id));
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/status/confirmed")
    public ResponseEntity<List<Reservation>> getConfirmedReservations() {
        List<Reservation> reservations = queryHandler.getActiveReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/status/cancelled")
    public ResponseEntity<List<Reservation>> getCancelledReservations() {
        List<Reservation> reservations = queryHandler.getCancelledReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/status/rejected")
    public ResponseEntity<List<Reservation>> getRejectedReservations() {
        List<Reservation> reservations = queryHandler.getRejectedReservations();
        return ResponseEntity.ok(reservations);
    }
}
