package be.ucll.craftsmanship.hotel.rooms.builders;

import be.ucll.craftsmanship.hotel.rooms.components.AirConditioning;
import be.ucll.craftsmanship.hotel.rooms.components.Wifi;
import java.util.List;


public interface RoomBuilder {
    void setRoomNumber(int roomNumber);
    void setReservationIds(List<Long> reservationIds);

    void setAirConditioning(AirConditioning airConditioning);
    void setWifi(Wifi wifi);
}