package be.ucll.craftsmanship.hotel.rooms.commands;

import be.ucll.craftsmanship.hotel.rooms.domain.RoomType;

public record CreateRoomCommand (int roomNumber, RoomType type) {

}
