package com.lth.hotelmanagement.service;

import com.lth.hotelmanagement.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;
    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoByRoomId(Long id) throws SQLException;
    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId, String roomType, BigDecimal price, byte[] photoBytes);

    Optional<Room> getRoomById(Long roomId);
}
