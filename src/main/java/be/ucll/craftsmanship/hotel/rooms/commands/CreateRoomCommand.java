package be.ucll.craftsmanship.hotel.rooms.commands;

public class CreateRoomCommand {
    private int roomNumber;
    private String type;

    public CreateRoomCommand() {
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
