package com.lth.hotelmanagement.controller;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.entity.Room;
import com.lth.hotelmanagement.exception.PhotoRetrievalException;
import com.lth.hotelmanagement.exception.ResourceNotFoundException;
import com.lth.hotelmanagement.response.BookingResponse;
import com.lth.hotelmanagement.response.RoomResponse;
import com.lth.hotelmanagement.service.impl.BookingService;
import com.lth.hotelmanagement.service.impl.RoomService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import response.ErrorResponse;
import response.SuccessResponse;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable("roomId") Long roomId,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) BigDecimal price,
                                                   @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {
        byte[] photoBytes = photo != null && photo.isEmpty() ? photo.getBytes() :
                roomService.getRoomPhotoByRoomId(roomId);
        Blob photoBlob = photoBytes != null && photoBytes.length > 0
                ? new SerialBlob(photoBytes) : null;
        Room theRoom = roomService.updateRoom(roomId, roomType, price, photoBytes);
        theRoom.setPhoto(photoBlob);
        RoomResponse roomResponse = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);
    }
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable("roomId") Long roomId) {
        Optional<Room> theRoom = roomService.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomResponse roomResponse = getRoomResponse(room);
            return ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found !"));
     }
     @GetMapping("/available-rooms")
     public ResponseEntity<List<RoomResponse>> getAvailableRooms(
             @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
             @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                                                 @RequestParam("roomType")String roomType) throws SQLException {
         System.out.println("checkIndATE" + checkInDate);
         System.out.println("checkIndATE" + checkOutDate);

        List<Room> availableRooms = roomService.getAvailableRooms(checkInDate, checkOutDate, roomType);
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : availableRooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0) {
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(photoBase64);
                roomResponses.add(roomResponse);
            }
        }
        if(roomResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(roomResponses);
        }
     }
}
