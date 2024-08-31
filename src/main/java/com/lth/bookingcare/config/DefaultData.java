package com.lth.bookingcare.config;

import com.lth.bookingcare.constant.RoleName;
import com.lth.bookingcare.entity.Hour;
import com.lth.bookingcare.entity.Role;
import com.lth.bookingcare.entity.User;
import com.lth.bookingcare.repository.HourRepository;
import com.lth.bookingcare.repository.RoleRepository;
import com.lth.bookingcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class DefaultData implements ApplicationRunner {
    @Autowired
    private HourRepository hourRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private void createHours() {
        Hour hour1 = new Hour("7h - 8h");
        Hour hour2 = new Hour("8h - 9h");
        Hour hour3 = new Hour("9h - 10h");
        Hour hour4 = new Hour("10h - 11h");
        Hour hour5 = new Hour("13h - 14h");
        Hour hour6 = new Hour("14h - 15h");
        Hour hour7 = new Hour("15h - 16h");
        Hour hour8 = new Hour("16h - 17h");
        if(hourRepository.findById(1L).isEmpty()) {
            hourRepository.saveAll(List.of(hour1,hour2,hour3,hour4,hour5,hour6,hour7,hour8));
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN);
        if(roleAdmin == null) {
            roleAdmin = new Role(RoleName.ROLE_ADMIN, "Manage all system");
            roleRepository.save(roleAdmin);
        }
        Role roleDoctor = roleRepository.findByName(RoleName.ROLE_DOCTOR);
        if(roleDoctor == null) {
            roleDoctor = new Role(RoleName.ROLE_DOCTOR, "Nothing");
            roleRepository.save(roleDoctor);
        }
        User user = userRepository.findByUsername("admin");
        if(user == null) {
            user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(roleAdmin);
            userRepository.save(user);
        }
        createHours();
    }
}
