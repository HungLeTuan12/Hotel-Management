package com.lth.bookingcare.service.impl;

import com.lth.bookingcare.entity.Schedule;
import com.lth.bookingcare.entity.ScheduleUser;
import com.lth.bookingcare.repository.ScheduleRepository;
import com.lth.bookingcare.repository.ScheduleUserRepository;
import com.lth.bookingcare.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService implements IScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleUserRepository scheduleUserRepository;
    @Override
    public List<Schedule> getAllSchedule(Long idDoctor) {
        List<ScheduleUser> scheduleUsers = scheduleUserRepository.findByUserId(idDoctor);
        List<Schedule> schedules = new ArrayList<>();
        scheduleUsers.forEach(e -> schedules.add(e.getSchedule()));
        return schedules;
    }

    @Override
    public Schedule getAllBy(Date date, Long id) {
        return scheduleRepository.findByHour_IdAndDate(id, date);
    }
}
