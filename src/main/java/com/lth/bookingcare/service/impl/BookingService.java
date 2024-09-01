package com.lth.bookingcare.service.impl;

import com.lth.bookingcare.constant.Status;
import com.lth.bookingcare.dto.BookingDTO;
import com.lth.bookingcare.entity.*;
import com.lth.bookingcare.exception.ResourceAlreadyExist;
import com.lth.bookingcare.exception.ResourceNotFoundException;
import com.lth.bookingcare.repository.*;
import com.lth.bookingcare.service.IBookingService;
import com.lth.bookingcare.utils.ConvertTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class BookingService implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleUserRepository scheduleUserRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HourRepository hourRepository;
    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).get();
    }

    @Override
    public void updateBooking(Long id, Status status) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isPresent()) {
            booking.get().setStatus(status);
            if(status.equals(Status.CONFIRMING))  booking.get().setToken(null);
            if(status.equals(Status.SUCCESS) || status.equals(Status.FAILURE)) {
                deleteScheduleOfDoctor(booking.get());
            }
        }
        bookingRepository.save(booking.get());
    }

    @Override
    public String createBooking(BookingDTO bookingDTO) {
        Optional<User> user = userRepository.findById(bookingDTO.getIdUser());
        if(user.isEmpty()) {
            throw new ResourceNotFoundException("Id doctor not found !");
        }
        user.get().getScheduleUsers().stream().forEach(
                e -> {
                    if(e.getSchedule().equals(bookingDTO.getDate()) && e.getSchedule().getHour().getId() == bookingDTO.getIdHour()) {
                        throw new ResourceAlreadyExist("Schedule is already exist !");
                    }
                }
        );
        // Create booking
        Booking booking = new Booking();
        convertDtoToEntity(bookingDTO, Status.PENDING);
        booking.setUser(user.get());
        bookingRepository.save(booking);
        // Save schedule of Doctor
        Optional<Hour> hour = hourRepository.findById(booking.getIdHour());
        if(hour.isEmpty()) {
            throw new ResourceNotFoundException("Schedule with id is not found !");
        }
        Schedule schedule = new Schedule(ConvertTimeUtil.stringToDate(bookingDTO.getDate()), hour.get());
        scheduleRepository.save(schedule);
        ScheduleUser scheduleUser = new ScheduleUser();
        scheduleUser.setUser(user.get());
        scheduleUser.setSchedule(schedule);
        scheduleUserRepository.save(scheduleUser);
        String s = "<h1>Thư xác nhận lịch khám</h1>\n" +
                "    <p>Tên người khám: "+bookingDTO.getName()+"</p>\n" +
                "    <p>Ngày sinh: "+bookingDTO.getDob()+"</p>\n" +
                "    <p>Giới tính: "+bookingDTO.getGender()+"</p>\n" +
                "    <p>Ngày khám: "+bookingDTO.getDate()+"</p>\n" +
                "    <p>Thòi gian khám: "+hour.get().getName()+"</p>\n" +
                "    <p>Tên bác sĩ khám: "+user.get().getFullName()+"</p>\n" +
                "    <p>Chuyên ngành: "+user.get().getMajor().getName()+"</p>\n" +
                "    <h4>Hãy nhấn xác nhận để lịch khám được hoàn thành đăng ký.</h4>\n" +
                "    <a href='http://localhost:8080/api/v1/confirm/"+booking.getId()+"?token="+booking.getToken()+"'><h2>Xác nhận</h2></a>";
        return s;
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        booking.setStatus(Status.FAILURE);
        deleteScheduleOfDoctor(booking);
        bookingRepository.save(booking);
    }

    @Override
    public void deleteScheduleOfDoctor(Booking booking) {
        Schedule schedule = scheduleService.getAllBy(booking.getDate(), booking.getId());
        ScheduleUser scheduleUser = scheduleUserRepository
                .findByUser_IdAndSchedule_Id(booking.getUser().getId(), schedule.getId());
        scheduleUserRepository.deleteById(scheduleUser.getId());
        scheduleRepository.deleteById(schedule.getId());
    }

    @Override
    public List<Booking> findByParam(Status status, Long id, String start, String end) {
        Date en = null, st = null;
        if(start != null) {
            st = ConvertTimeUtil.stringToDate(start);
        }
        if(end != null)   en = ConvertTimeUtil.stringToDate(end);
        return bookingRepository.findByCustom(id, status, st, en);
    }
    public Booking convertDtoToEntity(BookingDTO bookingDTO, Status status) {
        return new Booking(bookingDTO.getName(),bookingDTO.getDob(),bookingDTO.getPhone(),bookingDTO.getEmail(),bookingDTO.getGender(),
                bookingDTO.getAddress(),ConvertTimeUtil.stringToDate(bookingDTO.getDate()),bookingDTO.getIdHour(),status,bookingDTO.getNote(),
                UUID.randomUUID().toString());
    }
}
