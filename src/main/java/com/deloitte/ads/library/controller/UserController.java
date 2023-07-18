package com.deloitte.ads.library.controller;

import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.repository.UserRepository;
import com.deloitte.ads.library.service.MarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        marioService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String userEmail) {
        User user = marioService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
