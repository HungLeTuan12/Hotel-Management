package com.lth.bookingcare.controller;

import com.lth.bookingcare.dto.UserDTO;
import com.lth.bookingcare.entity.User;
import com.lth.bookingcare.service.impl.MajorService;
import com.lth.bookingcare.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MajorService majorService;
    @GetMapping("/doctor/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.getDoctorById(id);
            if(user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Not found doctor with id: " + id));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Get data success", user));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors(@RequestParam(value = "majorId", required = false) Long majorId,
                                           @RequestParam(value = "name", required = false) String fullName,
                                           @RequestParam(value = "status") String status,
                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "100") int size
                                           )
    {
        try {
            if (majorId != null && majorService.findById(majorId) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Major id is not found with id: " + majorId));
            }
            Pageable pageable = PageRequest.of(page - 1, size);
            List<User> users = userService.getAllDoctors(majorId, fullName, pageable, status
            );
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Get data success", users));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createDoctor(@RequestBody UserDTO userDTO) {
        try {
            if(userService.checkName(userDTO.getFullName())) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse<>("Username is already exist !"));
            }
            userService.createDoctor(userDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Create doctor successfully"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @PutMapping("/doctor/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable("id") Long id,
                                          @RequestBody UserDTO userDTO) {
        try {
            User user = userService.getDoctorById(id);
            if(user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("User is not found with id: " + id));
            }
            userService.updateDoctor(userDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Update successfully"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @PostMapping("/change-pass/{id}")
    public ResponseEntity<?> changePass(@PathVariable("id") Long id,
                                        @RequestBody String pass) {
        try {
            userService.changePassword(pass, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Update successfully"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @DeleteMapping("/delete-doctor/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable("id") Long id) {
        try {
            User user = userService.getDoctorById(id);
            if(user == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Id not found " + id);
            userService.deleteDoctor(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Delete successfully"));

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
}
