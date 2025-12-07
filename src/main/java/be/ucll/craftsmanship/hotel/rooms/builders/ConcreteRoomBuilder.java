package be.ucll.craftsmanship.hotel.rooms.builders;

import be.ucll.craftsmanship.hotel.rooms.components.AirConditioning;
import be.ucll.craftsmanship.hotel.rooms.components.Wifi;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.domain.RoomType;
import java.util.Collections;
import java.util.List;

public class ConcreteRoomBuilder implements RoomBuilder {
    private Integer roomNumber;
    private RoomType type;
    private List<Long> reservationIds = Collections.emptyList();

    private AirConditioning airConditioning;
    private Wifi wifi;

    @Override
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public void setType(RoomType type) {this.type = type;}

    @Override
    public void setReservationIds(List<Long> reservationIds) {
        this.reservationIds = reservationIds;
    }

    @Override
    public void setAirConditioning(AirConditioning airConditioning) {
        this.airConditioning = airConditioning;
    }

    @Override
    public void setWifi(Wifi wifi) {
        this.wifi = wifi;
    }

    @Override
    public Room build() {
        if (roomNumber == null) {
            throw new IllegalStateException("Cannot build Room: Room Number is required.");
        }
        if (type == null) {
            throw new IllegalStateException("Cannot build Room: Room Type is required.");
        }
        return new Room(roomNumber, type, reservationIds, airConditioning, wifi);
    }
}