package be.ucll.craftsmanship.hotel.rooms.domain;

import be.ucll.craftsmanship.hotel.rooms.components.AirConditioning;
import be.ucll.craftsmanship.hotel.rooms.components.Wifi;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.Collections;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final Integer roomNumber;

    @ElementCollection
    private final List<Long> reservationIds;

    @Transient
    private final AirConditioning airConditioning;

    @Transient
    private final Wifi wifi;

    protected Room() {
        this.roomNumber = null;
        this.reservationIds = Collections.emptyList();
        this.airConditioning = null;
        this.wifi = null;
    }

    public Room(Integer roomNumber, List<Long> reservationIds,
            AirConditioning airConditioning, Wifi wifi) {
        this.roomNumber = roomNumber;
        this.reservationIds = reservationIds != null ? reservationIds : Collections.emptyList();
        this.airConditioning = airConditioning;
        this.wifi = wifi;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public List<Long> getReservations() {
        return reservationIds;
    }

    public AirConditioning getAirConditioning() {
        return airConditioning;
    }

    public Wifi getWifi() {
        return wifi;
    }

    public Long getId() {
        return id;
    }
}