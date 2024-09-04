package com.lth.bookingcare.controller;

import com.lth.bookingcare.dto.RoleDTO;
import com.lth.bookingcare.entity.Role;
import com.lth.bookingcare.service.impl.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private RoleService roleService;
    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Role> roles = roleService.getAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Get all role: ", roles));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @PostMapping("/create-role")
    public ResponseEntity<?> createNewRole(@RequestBody RoleDTO roleDTO) {
        try {
            if(roleService.checkNameExists(roleDTO.getRoleName())) {
                return ResponseEntity.badRequest().body("This name is already exist !");
            }
            roleService.saveRole(roleDTO, null);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Create role successfully !"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @PutMapping("/update-role/{id}")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO,
                                        @PathVariable("id") Long id
                                        ) {
        try {
            if(!roleService.findById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Role is not found with id: " + id);
            }
            roleService.saveRole(roleDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Update role successfully !"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id) {
        try {
            if(!roleService.findById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Role is not found with id: " + id);
            }
            roleService.deleteRole(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Delete role successfully !"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ErrorResponse<>("Server error"));
        }
    }
}
