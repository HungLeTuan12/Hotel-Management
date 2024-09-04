package com.lth.bookingcare.controller;

import com.lth.bookingcare.entity.Hour;
import com.lth.bookingcare.repository.HourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HourController {
    @Autowired
    private HourRepository hourRepository;
    @GetMapping("/hours")
    public ResponseEntity<?> getAllHours() {
        try {
            List<Hour> hours = hourRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Hours: ", hours));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
}
