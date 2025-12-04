package be.ucll.craftsmanship.hotel.reservations.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String guest;

    private Date startDate;

    private Date endDate;

    public Reservation() {
    }

    public Reservation(String guest, Date startDate, Date endDate) {
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
