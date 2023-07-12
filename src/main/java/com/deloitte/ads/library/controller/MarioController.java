package com.deloitte.ads.library.controller;

import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.SentMario;
import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.service.MarioApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/marios")
public class MarioController {

    private final MarioApp marioApp;

    @Autowired
    public MarioController(MarioApp marioApp) {
        this.marioApp = marioApp;
    }

    @GetMapping()
    public ResponseEntity<Set<Mario>> getAllMarios() {
        Set<Mario> marios = marioApp.getMarioSet();
        return ResponseEntity.ok(marios);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMario(@RequestBody SentMario sentMario) {
        marioApp.sendMario(sentMario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<Set<SentMario>> getSentMarios(@PathVariable String userId) {
        User user = marioApp.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Set<SentMario> sentMarios = marioApp.findSentMariosBySender(userId);
        return ResponseEntity.ok(sentMarios);
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<Set<SentMario>> getReceivedMarios(@PathVariable String userId) {
        User user = marioApp.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Set<SentMario> receivedMarios = marioApp.findSentMariosByRecipient(userId);
        return ResponseEntity.ok(receivedMarios);
    }

}
