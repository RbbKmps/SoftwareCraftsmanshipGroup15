package be.ucll.craftsmanship.hotel.rooms.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer roomNumber;

    private List<Long> reservationIds;

    public Room() {
    }

    public Room(Integer roomNumber, List<Long> reservations) {
        this.roomNumber = roomNumber;
        this.reservationIds = reservations;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public List<Long> getReservations() {
        return reservationIds;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void addReservation(Long reservation) {
        this.reservationIds.add(reservation);
    }
}
