package be.ucll.craftsmanship.hotel.rooms.application;

import be.ucll.craftsmanship.hotel.rooms.builders.ConcreteRoomBuilder;
import be.ucll.craftsmanship.hotel.rooms.builders.RoomBuilder;
import be.ucll.craftsmanship.hotel.rooms.commands.CreateRoomCommand;
import be.ucll.craftsmanship.hotel.rooms.director.RoomDirector;
import be.ucll.craftsmanship.hotel.rooms.domain.Room;
import be.ucll.craftsmanship.hotel.rooms.infrastructure.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoomCommandHandler {

    private final RoomRepository roomRepository;
    private final RoomDirector roomDirector;

    public RoomCommandHandler(RoomRepository roomRepository, RoomDirector roomDirector) {
        this.roomRepository = roomRepository;
        this.roomDirector = roomDirector;
    }

    public Room createRoom(CreateRoomCommand command) {
        RoomBuilder builder = new ConcreteRoomBuilder();

        String type = command.getType();
        if (StringUtils.hasText(type) && "luxury".equalsIgnoreCase(type.trim())) {
            roomDirector.constructLuxuryRoom(builder, command.getRoomNumber());
        } else {
            roomDirector.constructNormalRoom(builder, command.getRoomNumber());
        }

        Room room = builder.build();
        return roomRepository.save(room);
    }
}
