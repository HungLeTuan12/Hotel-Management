package com.lth.bookingcare.controller;

import com.lth.bookingcare.dto.MajorDTO;
import com.lth.bookingcare.entity.Major;
import com.lth.bookingcare.service.impl.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MajorController {
    @Autowired
    private MajorService majorService;
    @PostMapping("/create-major")
    public ResponseEntity<?> createMajor(@RequestBody MajorDTO majorDTO) {
        try {
            if(majorService.checkNameMajor(majorDTO.getName())) {
                return ResponseEntity.badRequest().body(
                        new ErrorResponse<>("Major name is already exist !")
                );
            }
            majorService.saveMajor(majorDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Create successfully !"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @GetMapping("/major")
    public ResponseEntity<?> getAllMajor() {
        try {
            List<Major> majors = majorService.getAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Majors: ", majors));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @DeleteMapping("/delete-major/{id}")
    public ResponseEntity<?> deleteMajor(@PathVariable("id") Long id) {
        try {
            Major major = majorService.findById(id);
            if(major == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Major is not found !"));
            }
            majorService.deleteMajor(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Delete major successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @PutMapping("/update-major/{id}")
    public ResponseEntity<?> updateMajor(@RequestBody MajorDTO majorDTO,
                                         @PathVariable("id") Long id
                                         ){
        try {
            Major major = majorService.findById(id);
            if(major == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Major is not found !"));
            }
            majorService.updateMajor(majorDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Update major successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
}
