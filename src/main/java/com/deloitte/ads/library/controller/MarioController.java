package com.deloitte.ads.library.controller;

import com.deloitte.ads.library.repository.*;
import com.deloitte.ads.library.service.MarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/marios")
public class MarioController {

    private final MarioService marioService;

    @Autowired
    public MarioController(MarioService marioService) {
        this.marioService = marioService;
    }

    @GetMapping()
    public ResponseEntity<Set<Mario>> getAllMarios() {
        Set<Mario> marios = marioService.getMarioSet();
        return ResponseEntity.ok(marios);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMarios(@RequestBody Mario mario) {
        marioService.addMario(mario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMario(@RequestBody SendMarioRequest sentMarioRequest) {
        Mario mario = marioService.getMarioById(sentMarioRequest.getMarioId());
        if (mario == null) {
            return ResponseEntity.badRequest().body("Mario with ID " + sentMarioRequest.getMarioId() + " not found.");
        }

        User sender = marioService.getUserByEmail(sentMarioRequest.getSenderEmail());
        if (sender == null) {
            return ResponseEntity.badRequest().body("User with email " + sentMarioRequest.getSenderEmail() + " not found.");
        }

        Set<User> recipients = new HashSet<>();
        for (String recipientEmail : sentMarioRequest.getRecipientEmails()) {
            User recipient = marioService.getUserByEmail(recipientEmail);
            if (recipient != null) {
                recipients.add(recipient);
            }
        }

        SentMario sentMario = new SentMario(mario, sentMarioRequest.getComment(), sender, recipients);
        marioService.sendMarios(sentMario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/sent/{senderEmail}")
    public ResponseEntity<Set<SentMario>> getSentMarios(@PathVariable String senderEmail) {
        Set<SentMario> sentMarios = marioService.findSentMariosBySenderEmail(senderEmail);
        if (sentMarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sentMarios);
    }

    @GetMapping("/received/{recipientEmail}")
    public ResponseEntity<Set<SentMario>> getReceivedMarios(@PathVariable String recipientEmail) {
        Set<SentMario> receivedMarios = marioService.findSentMariosByRecipientEmail(recipientEmail);
        if (receivedMarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receivedMarios);
    }

//    @PostConstruct
//    public void initializeMarios() {
//        marioService.initializeMarios();
//    }
}

