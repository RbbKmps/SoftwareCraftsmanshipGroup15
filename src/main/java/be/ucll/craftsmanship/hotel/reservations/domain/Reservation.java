package be.ucll.craftsmanship.hotel.reservations.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long roomId;

    private String guest;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation() {
    }

    public Reservation(Long roomId, String guest, Date startDate, Date endDate) {
        this.roomId = roomId;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ReservationStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getGuest() {
        return guest;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void approve() {
        this.status = ReservationStatus.CONFIRMED;
    }

    public void reject() {
        this.status = ReservationStatus.REJECTED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

    @JsonIgnore
    public boolean isPending() {
        return status == ReservationStatus.PENDING;
    }

    @JsonIgnore
    public boolean isConfirmed() {
        return status == ReservationStatus.CONFIRMED;
    }

    @JsonIgnore
    public boolean isRejected() {
        return status == ReservationStatus.REJECTED;
    }

    @JsonIgnore
    public boolean isCancelled() {
        return status == ReservationStatus.CANCELLED;
    }
}
