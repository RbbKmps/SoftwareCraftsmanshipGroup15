package be.ucll.craftsmanship.hotel.rooms.application;

import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class RoomQueryHandler {
    private final RoomRepository roomRepository;


    public RoomQueryHandler(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
