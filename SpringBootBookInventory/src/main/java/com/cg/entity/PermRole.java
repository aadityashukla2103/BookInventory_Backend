package com.cg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permrole")
public class PermRole {

    @Id
    @Column(name = "RoleNumber")
    private Integer roleNumber;

    @Column(name = "PermRole")
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
