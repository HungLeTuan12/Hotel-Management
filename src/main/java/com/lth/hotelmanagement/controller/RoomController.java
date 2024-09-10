package com.lth.hotelmanagement.controller;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.entity.Room;
import com.lth.hotelmanagement.exception.PhotoRetrievalException;
import com.lth.hotelmanagement.response.BookingResponse;
import com.lth.hotelmanagement.response.RoomResponse;
import com.lth.hotelmanagement.service.impl.BookingService;
import com.lth.hotelmanagement.service.impl.RoomService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import response.ErrorResponse;
import response.SuccessResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private BookingService bookingService;
    @PostMapping("/")
    public ResponseEntity<?> addNewRoom(@RequestParam("photo") MultipartFile photo,
                                                   @RequestParam("roomType") String roomType,
                                                   @RequestParam("price") BigDecimal price
                                                   ) throws SQLException, IOException {
//        try {
            Room savedRoom = roomService.addNewRoom(photo, roomType, price);
            RoomResponse roomResponse = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getPrice());
            return ResponseEntity.ok().body(new SuccessResponse<>("Add successfully", savedRoom));
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse<>("Server error"));
//        }
    }
    @GetMapping("/room-types")
    public ResponseEntity<List<String>> getAllRoomTypes() {

            List<String> rooms = roomService.getAllRoomTypes();
            return ResponseEntity.ok().body(rooms);


    }
    @GetMapping("/")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingByRoomId(room.getId());
//        List<BookingResponse> bookingResponses = bookings
//                .stream()
//                .map(booking -> new BookingResponse(booking.getId(),
//                        booking.getCheckInDate(),
//                        booking.getCheckOutDate(),
//                        booking.getBookingConfirmCode())).toList();
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if(photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
            }
            catch (SQLException e) {
                throw new PhotoRetrievalException("Error retreiving photo !");
            }
        }
        return new RoomResponse(room.getId(), room.getRoomType(), room.getPrice(),
                room.isBooked(), photoBytes);
    }

    private List<BookedRoom> getAllBookingByRoomId(Long id) {
        return bookingService.getAllBookingByRoomId(id);
    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomId") Long roomId) {
        roomService.deleteRoom(roomId);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
