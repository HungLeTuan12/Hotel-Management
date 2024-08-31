package com.lth.bookingcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @NotBlank(message = "Full name is required field !")
    @Size(min = 6, max = 64, message = "Length of full name must between 6 and 64")
    private String fullName;
    @NotBlank(message = "Username is required field !")
    @Size(min = 6, max = 64, message = "Length of username must between 6 and 64")
    private String username;
    @NotBlank(message = "Password is required field !")
    @Size(min = 6, max = 64, message = "Length of password must between 6 and 64")
    private String password;
    private String phone;
    private String description;
    private String email;
    private String status;
    private Long majorId;
    private String image;
    private boolean enabled;
    // Constructor

    public UserDTO() {
    }

    public UserDTO(String fullName, String username, String password, String phone, String description, String email, String status, Long majorId) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.description = description;
        this.email = email;
        this.status = status;
        this.majorId = majorId;
    }
    // Getter and setter

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
