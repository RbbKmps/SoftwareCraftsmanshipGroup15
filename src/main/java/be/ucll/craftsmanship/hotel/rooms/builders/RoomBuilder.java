package be.ucll.craftsmanship.hotel.rooms.builders;

import be.ucll.craftsmanship.hotel.rooms.components.AirConditioning;
import be.ucll.craftsmanship.hotel.rooms.components.Wifi;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.domain.RoomType;
import java.util.List;

public interface RoomBuilder {
    void setRoomNumber(int roomNumber);

    void setType(RoomType type);

    void setReservationIds(List<Long> reservationIds);

    void setAirConditioning(AirConditioning airConditioning);

    void setWifi(Wifi wifi);

    Room build();
}