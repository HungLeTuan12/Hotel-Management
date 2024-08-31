package com.lth.bookingcare.dto;

import com.lth.bookingcare.constant.RoleName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RoleDTO {
    @NotNull(message = "Name of role can't blank !")
    private RoleName roleName;
    @Size(max = 64, message = "Length of description must shorter than 64 !")
    private String description;
    // Data

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
