package com.cg.dto;

import jakarta.validation.constraints.*;

public class PermRoleDto {

    @NotNull(message = "Role number is required")
    @Positive(message = "Role number must be greater than 0")
    private Integer roleNumber;

    @NotBlank(message = "Permission role is required")
    @Size(max = 30, message = "Permission role must be less than 30 characters")
    private String permRole;

    public Integer getRoleNumber() {
        return roleNumber;
    }

    public void setRoleNumber(Integer roleNumber) {
        this.roleNumber = roleNumber;
    }

    public String getPermRole() {
        return permRole;
    }

    public void setPermRole(String permRole) {
        this.permRole = permRole;
    }
}