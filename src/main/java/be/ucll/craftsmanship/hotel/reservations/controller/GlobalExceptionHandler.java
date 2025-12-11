package be.ucll.craftsmanship.hotel.reservations.controller;

import be.ucll.craftsmanship.hotel.reservations.exceptions.RoomNotFoundException;
import be.ucll.craftsmanship.hotel.reservations.exceptions.ReservationRejectedException;
import be.ucll.craftsmanship.hotel.reservations.exceptions.ReservationTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomNotFound(RoomNotFoundException e) {
        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                "ROOM_NOT_FOUND",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ReservationRejectedException.class)
    public ResponseEntity<ErrorResponse> handleReservationRejected(ReservationRejectedException e) {
        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                "RESERVATION_REJECTED",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ReservationTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeout(ReservationTimeoutException e) {
        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                "RESERVATION_TIMEOUT",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                "INVALID_INPUT",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException e) {
        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                "INVALID_STATE",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
