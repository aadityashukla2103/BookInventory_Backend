package com.cg.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String userName;
    private String password;

    @ManyToOne
    @JoinColumn(name = "RoleNumber")
    private PermRole role;
}