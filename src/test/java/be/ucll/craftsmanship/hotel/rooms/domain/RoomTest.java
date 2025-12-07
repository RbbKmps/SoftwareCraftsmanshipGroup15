package be.ucll.craftsmanship.hotel.rooms.domain;

import be.ucll.craftsmanship.hotel.rooms.components.AirConditioning;
import be.ucll.craftsmanship.hotel.rooms.components.Wifi;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void getRoomNumber() {
        Room room = new Room(101, RoomType.NORMAL, null, null, null);
        assertEquals(101, room.getRoomNumber());
    }

    @Test
    void getReservations() {
        List<Long> reservations = List.of(1L, 2L, 3L);
        Room room = new Room(101, RoomType.NORMAL, reservations, null, null);
        assertEquals(reservations, room.getReservations());
    }

    @Test
    void getType() {
        Room room = new Room(101, RoomType.NORMAL, null, null, null);
        assertEquals(RoomType.NORMAL, room.getType());
    }

    @Test
    void getAirConditioning() {
        AirConditioning airConditioning = new AirConditioning(21);
        Wifi wifi = new Wifi("HotelWiFi", 100);
        Room room = new Room(101, RoomType.LUXURY, null, airConditioning, wifi);
        assertEquals(airConditioning, room.getAirConditioning());
    }

    @Test
    void getWifi() {
        AirConditioning airConditioning = new AirConditioning(21);
        Wifi wifi = new Wifi("HotelWiFi", 100);
        Room room = new Room(101, RoomType.LUXURY, null, airConditioning, wifi);
        assertEquals(wifi, room.getWifi());
    }

    @Test
    void getId() {
        Room room = new Room(101, RoomType.NORMAL, null, null, null);
        assertNull(room.getId());
    }
}