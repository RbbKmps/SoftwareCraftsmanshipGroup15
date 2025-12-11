package be.ucll.craftsmanship.hotel.reservations.application;

import be.ucll.craftsmanship.hotel.reservations.commands.CancelReservationCommand;
import be.ucll.craftsmanship.hotel.reservations.commands.CreateReservationCommand;
import be.ucll.craftsmanship.hotel.reservations.domain.Reservation;
import be.ucll.craftsmanship.hotel.reservations.domain.ReservationStatus;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationEventPublisher;
import be.ucll.craftsmanship.hotel.reservations.events.ReservationPendingEvent;
import be.ucll.craftsmanship.hotel.reservations.exceptions.RoomNotFoundException;
import be.ucll.craftsmanship.hotel.reservations.exceptions.ReservationRejectedException;
import be.ucll.craftsmanship.hotel.reservations.exceptions.ReservationTimeoutException;
import be.ucll.craftsmanship.hotel.reservations.infrastructure.ReservationRepository;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationCommandHandler {
    private static final int TIMEOUT_SECONDS = 10;
    private static final int POLL_INTERVAL_MS = 100;

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ReservationEventPublisher eventPublisher;

    public ReservationCommandHandler(ReservationRepository reservationRepository,
                                   RoomRepository roomRepository,
                                   ReservationEventPublisher eventPublisher) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.eventPublisher = eventPublisher;
    }

    public Reservation createReservation(CreateReservationCommand command) {
        if (command.guest() == null || command.guest().trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be empty");
        }

        if (command.startDate() == null || command.endDate() == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }

        if (command.startDate().after(command.endDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (!roomRepository.existsById(command.roomId())) {
            throw new RoomNotFoundException(command.roomId());
        }

        Reservation reservation = new Reservation(
                command.roomId(),
                command.guest(),
                command.startDate(),
                command.endDate()
        );
        Reservation savedReservation = reservationRepository.save(reservation);

        ReservationPendingEvent event = new ReservationPendingEvent(
                savedReservation.getId(),
                savedReservation.getRoomId(),
                savedReservation.getGuest(),
                savedReservation.getStartDate(),
                savedReservation.getEndDate()
        );
        eventPublisher.publish(event);

        waitForApproval(savedReservation.getId(), TIMEOUT_SECONDS);

        Reservation confirmed = reservationRepository.findById(savedReservation.getId())
                .orElseThrow(() -> new IllegalStateException("Reservation not found after approval wait"));

        if (confirmed.isConfirmed()) {
            return confirmed;
        } else if (confirmed.isRejected()) {
            throw new ReservationRejectedException("Room has date conflict");
        } else {
            throw new ReservationTimeoutException("Room did not respond in time");
        }
    }

    public void cancelReservation(CancelReservationCommand command) {
        Reservation reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + command.reservationId()));

        if (reservation.isCancelled()) {
            throw new IllegalStateException("Reservation is already cancelled");
        }

        reservation.cancel();
        reservationRepository.save(reservation);
    }

    private ReservationStatus waitForApproval(Long reservationId, int timeoutSeconds) {
        long deadline = System.currentTimeMillis() + (timeoutSeconds * 1000);

        while (System.currentTimeMillis() < deadline) {
            Reservation res = reservationRepository.findById(reservationId)
                    .orElse(null);

            if (res == null) {
                return ReservationStatus.PENDING;
            }

            if (res.getStatus() != ReservationStatus.PENDING) {
                return res.getStatus();
            }

            try {
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ReservationTimeoutException("Interrupted while waiting for approval");
            }
        }

        return ReservationStatus.PENDING;
    }
}
