package com.lth.hotelmanagement.service;

import com.lth.hotelmanagement.entity.BookedRoom;
import com.lth.hotelmanagement.entity.User;
import com.lth.hotelmanagement.exception.UserAlreadyExistsException;

import java.util.List;

public interface IUserService {
    User registerUser(User user) throws UserAlreadyExistsException;
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(Long id);
    List<BookedRoom> getBookingHistory(Long userId);
}
