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

@RestController
@RequestMapping("/marios")
public class MarioController {

    private final MarioApp marioApp;

    @Autowired
    public MarioController(MarioApp marioApp) {
        this.marioApp = marioApp;
    }

    @GetMapping()
    public ResponseEntity<List<Mario>> getAllMarios() {
        List<Mario> marios = marioApp.getMarioList();
        return ResponseEntity.ok(marios);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMario(@RequestBody SentMario sentMario) {
        marioApp.sendMario(sentMario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<SentMario>> getSentMarios(@PathVariable String userId) {
        User user = marioApp.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<SentMario> sentMarios = marioApp.findSentMariosBySender(userId);
        return ResponseEntity.ok(sentMarios);
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<List<SentMario>> getReceivedMarios(@PathVariable String userId) {
        User user = marioApp.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<SentMario> receivedMarios = marioApp.findSentMariosByRecipient(userId);
        return ResponseEntity.ok(receivedMarios);
    }

}
