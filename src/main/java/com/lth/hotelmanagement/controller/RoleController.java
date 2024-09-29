package com.lth.hotelmanagement.controller;

import com.lth.hotelmanagement.entity.Role;
import com.lth.hotelmanagement.entity.User;
import com.lth.hotelmanagement.exception.RoleAlreadyExistException;
import com.lth.hotelmanagement.exception.UserAlreadyExistsException;
import com.lth.hotelmanagement.service.impl.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private RoleService roleService;
    @GetMapping("/")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.FOUND);
    }
    @PostMapping("/")
    public ResponseEntity<?> createRole(@RequestBody Role theRole) {
        try {
            roleService.createRole(theRole);
            return ResponseEntity.ok("New role created successfully !!");
        }
        catch (RoleAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
    }
    @PostMapping("/remove-all/{roleId}")
    public Role removeAllUserFromRole(@PathVariable("roleId") Long roleId) {
        return roleService.removeAllUsersFromRole(roleId);
    }
    @PostMapping("/remove-user")
    public User removeUserFromRole(
            @RequestParam("email") String email,
            @RequestParam("roleId") Long roleId
    ) {
        return roleService.removeUserFromRole(email, roleId);
    }
    @PostMapping("/assign-user")
    public User assignUserToRole(@RequestParam("userId") Long userId,
                                 @RequestParam("roleId") Long roleId) throws UserAlreadyExistsException {
        return roleService.assignRoleToUser(userId, roleId);
    }
}
