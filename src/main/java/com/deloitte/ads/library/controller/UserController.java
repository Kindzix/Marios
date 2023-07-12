package com.deloitte.ads.library.controller;

import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.service.MarioApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final MarioApp marioApp;

    @Autowired
    public UserController(MarioApp marioApp) {
        this.marioApp = marioApp;
    }

    @GetMapping()
    public ResponseEntity<Set<User>> getAllUsers() {
        Set<User> users = marioApp.getUserSet();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        marioApp.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = marioApp.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
