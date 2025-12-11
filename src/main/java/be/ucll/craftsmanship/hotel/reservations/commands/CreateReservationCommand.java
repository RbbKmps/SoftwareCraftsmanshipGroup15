package be.ucll.craftsmanship.hotel.reservations.commands;

import java.util.Date;

public record CreateReservationCommand(Long roomId, String guest, Date startDate, Date endDate) {
}
