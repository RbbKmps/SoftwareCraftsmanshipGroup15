package be.ucll.craftsmanship.hotel.rooms.application;

import be.ucll.craftsmanship.hotel.rooms.builders.ConcreteRoomBuilder;
import be.ucll.craftsmanship.hotel.rooms.builders.RoomBuilder;
import be.ucll.craftsmanship.hotel.rooms.commands.CreateRoomCommand;
import be.ucll.craftsmanship.hotel.rooms.director.RoomDirector;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.domain.RoomType;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomCommandHandler {

    private final RoomRepository roomRepository;
    private final RoomDirector roomDirector;

    public RoomCommandHandler(RoomRepository roomRepository, RoomDirector roomDirector) {
        this.roomRepository = roomRepository;
        this.roomDirector = roomDirector;
    }

    public Room createRoom(CreateRoomCommand command) {
        // Check if room number already exists
        Room existingRoom = roomRepository.findByRoomNumber(command.roomNumber());
        if (existingRoom != null) {
            throw new IllegalArgumentException("Room number " + command.roomNumber() + " already exists");
        }

        RoomBuilder builder = new ConcreteRoomBuilder();

        RoomType type = command.type();
        builder.setType(type);
        if (type == RoomType.LUXURY) {
            roomDirector.constructLuxuryRoom(builder, command.roomNumber());
        } else {
            roomDirector.constructNormalRoom(builder, command.roomNumber());
        }

        Room room = builder.build();
        return roomRepository.save(room);
    }
}
