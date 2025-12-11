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
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Long> reservationIds;

    @Embedded
    private final AirConditioning airConditioning;

    @Embedded
    private final Wifi wifi;

    @Transient
    private Map<Long, DateRange> reservationsByDate = new HashMap<>();

    protected Room() {
        this.roomNumber = null;
        this.type = null;
        this.reservationIds = new ArrayList<>();
        this.airConditioning = null;
        this.wifi = null;
    }

    public Room(Integer roomNumber, RoomType type, List<Long> reservationIds,
            AirConditioning airConditioning, Wifi wifi) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.reservationIds = reservationIds != null ? new ArrayList<>(reservationIds) : new ArrayList<>();
        this.airConditioning = airConditioning;
        this.wifi = wifi;
        this.reservationsByDate = new HashMap<>();
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public List<Long> getReservations() {
        return new ArrayList<>(reservationIds);
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

    public boolean hasConflict(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }

        for (DateRange range : reservationsByDate.values()) {
            if (range.conflicts(startDate, endDate)) {
                return true;
            }
        }
        return false;
    }

    public void addReservation(Long reservationId, Date startDate, Date endDate) {
        if (!reservationIds.contains(reservationId)) {
            reservationIds.add(reservationId);
        }
        reservationsByDate.put(reservationId, new DateRange(startDate, endDate));
    }

    public void removeReservation(Long reservationId) {
        reservationIds.remove(reservationId);
        reservationsByDate.remove(reservationId);
    }

    private static class DateRange {
        private final Date startDate;
        private final Date endDate;

        DateRange(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        boolean conflicts(Date otherStart, Date otherEnd) {
            return !(endDate.before(otherStart) || startDate.after(otherEnd));
        }
    }
}
