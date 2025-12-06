package be.ucll.craftsmanship.hotel.rooms.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ucll.craftsmanship.hotel.rooms.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomNumber(Integer roomNumber);
}
