package com.deloitte.ads.library.controller;

import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.repository.UserRequest;
import com.deloitte.ads.library.service.MarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final MarioService marioService;

    @Autowired
    public UserController(MarioService marioService) {
        this.marioService = marioService;
    }

    @GetMapping()
    public ResponseEntity<Set<User>> getAllUsers() {
        Set<User> users = marioService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUserByUuid(@PathVariable String uuid) {
        try {
            UUID userUuid = UUID.fromString(uuid);
            User user = marioService.getUserByUuid(userUuid);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest) {
        try {
            User newUser = marioService.addUserFromUserRequest(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
