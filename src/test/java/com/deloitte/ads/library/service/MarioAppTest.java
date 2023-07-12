package com.deloitte.ads.library.service;

import com.deloitte.ads.library.controller.MarioController;
import com.deloitte.ads.library.controller.UserController;
import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.SentMario;
import com.deloitte.ads.library.repository.User;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class MarioAppTest {

    @Autowired
    private MarioController marioController;

    @Autowired
    private UserController userController;

    @Autowired
    private MarioApp marioApp;



    @Test
    void addMario() {
        Mario mario = new Mario("M6", "Test Mario");
        marioApp.addMario(mario);

        Set<Mario> marioSet = marioApp.getMarioSet();
        Assertions.assertTrue(marioSet.contains(mario));
    }

    @Test
    void addUser() {
        User user = new User("4", "Test", "Test", "Test.Test@example.com");
        marioApp.addUser(user);

        Set<User> userSet = marioApp.getUserSet();
        Assertions.assertTrue(userSet.contains(user));
    }

    @Test
    void sendMario() {
        Mario mario = new Mario("M6", "Test Mario");
        User sender = marioApp.getUserById("1");
        User recipient = marioApp.getUserById("2");
        SentMario sentMario = new SentMario(mario, "Test Comment", sender.getIdUser(), Sets.newHashSet(recipient));

        marioApp.sendMario(sentMario);

        Set<SentMario> sentMarios = marioApp.findSentMariosBySender(sender.getIdUser());
        Assertions.assertTrue(sentMarios.contains(sentMario));

        Set<SentMario> receivedMarios = marioApp.findSentMariosByRecipient(recipient.getIdUser());
        Assertions.assertTrue(receivedMarios.contains(sentMario));
    }

    @Test
    void findSentMariosBySender() {
        String senderId = "1";
        Set<SentMario> sentMarios = marioApp.findSentMariosBySender(senderId);
        Assertions.assertNotNull(sentMarios);
    }

    @Test
    void findSentMariosByRecipient() {
        String recipientId = "2";
        Set<SentMario> sentMarios = marioApp.findSentMariosByRecipient(recipientId);
        Assertions.assertNotNull(sentMarios);
    }
}