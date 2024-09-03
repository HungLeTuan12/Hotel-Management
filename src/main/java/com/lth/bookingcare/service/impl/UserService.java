package com.lth.bookingcare.service.impl;

import com.lth.bookingcare.config.CustomUserDetail;
import com.lth.bookingcare.config.JwtService;
import com.lth.bookingcare.dto.UserDTO;
import com.lth.bookingcare.entity.Major;
import com.lth.bookingcare.entity.Role;
import com.lth.bookingcare.entity.User;
import com.lth.bookingcare.exception.ResourceAlreadyExist;
import com.lth.bookingcare.exception.ResourceNotFoundException;
import com.lth.bookingcare.repository.MajorRepository;
import com.lth.bookingcare.repository.RoleRepository;
import com.lth.bookingcare.repository.UserRepository;
import com.lth.bookingcare.request.LoginRequest;
import com.lth.bookingcare.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Map<String, String> login(LoginRequest loginRequest) {
        try {
            Map<String, String> map = new HashMap<>();
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtService.generateToken((CustomUserDetail) userDetails);
            map.put("accessToken", jwt);
            map.put("roleId", String.valueOf(((CustomUserDetail) userDetails).getRoleId()));
            map.put("userId", String.valueOf(((CustomUserDetail) userDetails).getUserId()));
            return map;
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password !");
        }
    }

    @Override
    public boolean checkName(String name) {
        return userRepository.findByUsername(name) != null;
    }

    @Override
    public User getDoctorById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteDoctor(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllDoctors(Long majorId, String name, Pageable pageable, String status) {
        Boolean enabled = null;
        if(status != null)  enabled = status.equals("true");
        return userRepository.getAllDoctors(majorId, name, enabled, pageable).stream().collect(Collectors.toList());
    }

    @Override
    public void updateDoctor(UserDTO userDTO, Long id) {
        User user = userRepository.findById(id).get();
        if(user.getUsername().equals(userDTO.getUsername())) {
            throw new ResourceAlreadyExist("Username is already exist !");
        }
        Major major = majorRepository.findById(id).get();
        if(major == null) {
            throw new ResourceNotFoundException("Major is not found !");
        }
        else {
            user.setMajor(major);
        }
        convertDTOtoEntity(userDTO, user);
        if(!user.getStatus().equals("new")) {
            user.setEnabled(false);
            user.setStatus(user.getStatus());
        }
        else {
            user.setStatus("new");
            user.setEnabled(true);
        }
        userRepository.save(user);
    }

    @Override
    public void createDoctor(UserDTO userDTO) {
        User user = new User();
        Role role = roleRepository.findById(2L).get();
        Major major = majorRepository.findById(userDTO.getMajorId()).get();
        user.setRole(role);
        user.setMajor(major);
        convertDTOtoEntity(userDTO, user);
        user.setStatus("new");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void changePassword(String password, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found doctor with id: " + id));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    public void convertDTOtoEntity(UserDTO userDTO, User user) {
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setDescription(userDTO.getDescription());
        user.setImage(userDTO.getImage());
    }
}
