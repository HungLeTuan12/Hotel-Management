package com.lth.hotelmanagement.service.impl;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.entity.Role;
import com.lth.hotelmanagement.entity.User;
import com.lth.hotelmanagement.exception.UserAlreadyExistsException;
import com.lth.hotelmanagement.repository.BookedRoomRepository;
import com.lth.hotelmanagement.repository.RoleRepository;
import com.lth.hotelmanagement.repository.UserRepository;
import com.lth.hotelmanagement.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail() + " already exist !!");
        }
        System.out.println("password" + user.getEmail());
        System.out.println("password" + user.getLastName());

        System.out.println("password" + user.getPassword());
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found in the database"));

        System.out.println("role: " + userRole);
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found !!!"));
    }

    @Override
    public List<BookedRoom> getBookingHistory(Long userId) {
        return bookedRoomRepository.findByUserId(userId);
    }
}
