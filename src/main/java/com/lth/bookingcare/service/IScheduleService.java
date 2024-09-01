package com.lth.bookingcare.service;

import com.lth.bookingcare.entity.Schedule;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface IScheduleService {
    List<Schedule> getAllSchedule(Long idDoctor);
    Schedule getAllBy(Date date, Long id);
}
