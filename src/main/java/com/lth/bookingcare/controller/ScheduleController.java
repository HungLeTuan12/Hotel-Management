package com.lth.bookingcare.controller;

import com.lth.bookingcare.entity.Schedule;
import com.lth.bookingcare.service.impl.ScheduleService;
import com.lth.bookingcare.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("api/v1")

public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;
    public ResponseEntity<?> getAllSchedule(@RequestParam("idDoctor") Long idDoctor) {
        try {
            if(userService.getDoctorById(idDoctor) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Doctor id is not found !"));
            }
            List<Schedule> schedules = scheduleService.getAllSchedule(idDoctor);
            return ResponseEntity.ok().body(
                    new SuccessResponse<>("Schedules", schedules)
            );
         }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
}
