package be.ucll.craftsmanship.hotel.rooms.director;

import be.ucll.craftsmanship.hotel.rooms.builders.RoomBuilder;
import be.ucll.craftsmanship.hotel.rooms.components.AirConditioning;
import be.ucll.craftsmanship.hotel.rooms.components.Wifi;


public class RoomDirector {
    public void constructNormalRoom(RoomBuilder builder, int number) {
        builder.setRoomNumber(number);
    }

    public void constructLuxuryRoom(RoomBuilder builder, int number) {
        builder.setRoomNumber(number);

        AirConditioning ac = new AirConditioning(21);
        ac.turnOn();
        builder.setAirConditioning(ac);

        Wifi wifi = new Wifi("FiberNet Premium", 500);
        builder.setWifi(wifi);
    }
}