package com.deloitte.ads.library.controller;

import com.deloitte.ads.library.repository.*;
import com.deloitte.ads.library.service.MarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.UUID;

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
        Set<Mario> marios = marioService.getAllMarios();
        return ResponseEntity.ok(marios);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Mario> getMarioByUuid(@PathVariable String uuid) {
        try {
            UUID marioUuid = UUID.fromString(uuid);
            Mario mario = marioService.getMarioByUuid(marioUuid);
            if (mario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(mario);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMarios(@RequestBody MarioRequest marioRequest) {
        try {
            marioService.addMarioFromMarioRequest(marioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMario(@RequestBody SendMarioRequest sentMarioRequest) {
        try {
            marioService.sendMariosFromSendMarioRequest(sentMarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/sent/{senderUuid}")
    public ResponseEntity<Set<SentMario>> getSentMarios(@PathVariable String senderUuid) {
        try {
            UUID senderUserUuid = UUID.fromString(senderUuid);
            Set<SentMario> sentMarios = marioService.findSentMariosBySenderUuid(senderUserUuid);
            if (sentMarios.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(sentMarios);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/received/{recipientUuid}")
    public ResponseEntity<Set<SentMario>> getReceivedMarios(@PathVariable String recipientUuid) {
        try {
            UUID recipientUserUuid = UUID.fromString(recipientUuid);
            Set<SentMario> receivedMarios = marioService.findSentMariosByRecipientUuid(recipientUserUuid);
            if (receivedMarios.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(receivedMarios);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostConstruct
    public void initializeMarios() {
        marioService.initializeMarios();
    }
}
