package com.lth.bookingcare.service;

import com.lth.bookingcare.dto.UserDTO;
import com.lth.bookingcare.entity.User;
import com.lth.bookingcare.request.LoginRequest;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

public interface IUserService {
    Map<String, String> login(LoginRequest loginRequest);
    boolean checkName(String name);
    User getDoctorById(Long id);
    void deleteDoctor(Long id);
    List<User> getAllDoctors(Long majorId, String name, Pageable pageable, String status);
    void updateDoctor(UserDTO userDTO, Long id);
    void createDoctor(UserDTO userDTO);
    void changePassword(String password, Long id);
}
