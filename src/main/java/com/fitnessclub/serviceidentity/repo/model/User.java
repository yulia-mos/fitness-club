package com.fitnessclub.serviceidentity.repo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public final class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String role;

    private String firstName;
    private String lastName;
    @Getter
    @Setter
    private Boolean blocked;

    public User() {
    }

    public User(String username, String role, String fistName, String lastName, Boolean blocked) {
        this.username = username;
        this.role = role;
        this.firstName = fistName;
        this.lastName = lastName;
        this.blocked=blocked;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fistName) {
        this.firstName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
