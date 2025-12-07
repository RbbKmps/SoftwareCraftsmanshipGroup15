package be.ucll.craftsmanship.hotel.rooms.domain;

import be.ucll.craftsmanship.hotel.rooms.components.AirConditioning;
import be.ucll.craftsmanship.hotel.rooms.components.Wifi;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import java.util.Collections;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private final Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private final RoomType type;

    @ElementCollection
    private final List<Long> reservationIds;

    @Embedded
    private final AirConditioning airConditioning;

    @Embedded
    private final Wifi wifi;

    protected Room() {
        this.roomNumber = null;
        this.type = null;
        this.reservationIds = Collections.emptyList();
        this.airConditioning = null;
        this.wifi = null;
    }

    public Room(Integer roomNumber, RoomType type, List<Long> reservationIds,
            AirConditioning airConditioning, Wifi wifi) {
        this.roomNumber = roomNumber;
        this.type = type;
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

    public RoomType getType() {
        return type;
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