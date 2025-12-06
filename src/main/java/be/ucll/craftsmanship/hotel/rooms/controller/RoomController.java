package be.ucll.craftsmanship.hotel.rooms.controller;

import be.ucll.craftsmanship.hotel.rooms.application.RoomCommandHandler;
import be.ucll.craftsmanship.hotel.rooms.application.RoomQueryHandler;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomCommandHandler commandHandler;
    private final RoomQueryHandler queryHandler;

    public RoomController(RoomCommandHandler commandHandler, RoomQueryHandler queryHandler) {
        this.commandHandler = commandHandler;
        this.queryHandler = queryHandler;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return queryHandler.findAll();
    }
}
