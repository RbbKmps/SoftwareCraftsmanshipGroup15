package be.ucll.craftsmanship.hotel.rooms.controller;

import be.ucll.craftsmanship.hotel.rooms.application.RoomCommandHandler;
import be.ucll.craftsmanship.hotel.rooms.application.RoomQueryHandler;
import be.ucll.craftsmanship.hotel.rooms.commands.CreateRoomCommand;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.queries.GetRoomQuery;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/by-number")
    public Room getRoomByRoomNumber(@RequestBody GetRoomQuery query) {
        return queryHandler.getRoomByRoomNumber(query);
    }

    @PostMapping
    public Room createRoom(@RequestBody CreateRoomCommand command) {
        return commandHandler.createRoom(command);
    }
}
