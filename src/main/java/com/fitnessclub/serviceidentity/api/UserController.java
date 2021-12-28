package com.fitnessclub.serviceidentity.api;

import com.fitnessclub.serviceidentity.dto.UserDto;
import com.fitnessclub.serviceidentity.repo.model.User;
import com.fitnessclub.serviceidentity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public final class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> index(){
        final List<User> users=userService.fetchAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable long id) {
        try {
            final User user = userService.fetchById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDto user){
        final String username=user.getUsername();
        final String role = user.getRole();
        final String firstName = user.getFirstName();
        final String lastName=user.getLastName();
        final Boolean blocked=user.getBlocked();
        final long id=userService.create(username, role, firstName, lastName, blocked);
        final String location= String.format("/users/%d", id);
        return ResponseEntity.created((URI.create(location))).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UserDto user){
        final String username=user.getUsername();
        final String role = user.getRole();
        final String firstName = user.getFirstName();
        final String lastName=user.getLastName();
        final Boolean blocked=user.getBlocked();
        try{
            userService.update(id, username, role, firstName, lastName, blocked);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
