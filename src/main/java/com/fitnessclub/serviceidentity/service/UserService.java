package com.fitnessclub.serviceidentity.service;

import com.fitnessclub.serviceidentity.repo.UserRepo;
import com.fitnessclub.serviceidentity.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@Service
public final class UserService {
    private final UserRepo userRepo;
    private final String abonnementURL="http://localhost:8080/abonnements";


    public long create(String username, String role, String firstName, String lastName, Boolean blocked) {
        final User user = new User(username, role, firstName, lastName, blocked);
        final User savedUser=userRepo.save(user);
        return savedUser.getId();
    }

    public User fetchById(long id) {
        final Optional<User> maybeUser=userRepo.findById(id);
        if (maybeUser.isEmpty()) throw  new IllegalArgumentException("User not found");
        else return maybeUser.get();
    }

    public List<User> fetchAll() {
        return userRepo.findAll();
    }

    public void update(long id, String username, String role, String firstName, String lastName, Boolean blocked) {
        final Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty()) throw  new IllegalArgumentException("User not found");
        User user=maybeUser.get();
        if (username!=null && !username.isBlank()) user.setUsername(username);
        if (role!=null && !role.isBlank()) user.setRole(role);
        if (firstName!=null && !firstName.isBlank()) user.setFirstName(firstName);
        if (lastName!=null && !lastName.isBlank()) user.setLastName(lastName);
        if (blocked!=null) user.setBlocked(blocked);
        userRepo.save(user);
    }

    public void deleteById(long id) {
        userRepo.deleteById(id);
    }

}
