package be.ucll.craftsmanship.hotel.reservations.controller;

public record ErrorResponse(
        String message,
        String errorCode,
        long timestamp
) {
}
