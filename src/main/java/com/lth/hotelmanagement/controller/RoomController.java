package com.lth.hotelmanagement.controller;

import com.lth.hotelmanagement.entity.Room;
import com.lth.hotelmanagement.response.RoomResponse;
import com.lth.hotelmanagement.service.impl.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import response.ErrorResponse;
import response.SuccessResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
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
}
