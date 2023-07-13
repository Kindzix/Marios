package com.deloitte.ads.library.service;

import com.deloitte.ads.library.controller.MarioController;
import com.deloitte.ads.library.controller.UserController;
import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.SentMario;
import com.deloitte.ads.library.repository.User;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
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

        Mario mario2 = new Mario(null, "Test Mario");
        marioApp.addMario(mario2);

        Set<Mario> marioSet2 = marioApp.getMarioSet();
        Assertions.assertFalse(marioSet2.contains(mario2));

        Mario mario3 = new Mario("M6", null);
        marioApp.addMario(mario3);

        Set<Mario> marioSet3 = marioApp.getMarioSet();
        Assertions.assertFalse(marioSet3.contains(mario3));

        Mario mario4 = new Mario("M6", "Test Marios");
        marioApp.addMario(mario4);

        Set<Mario> marioSet4 = marioApp.getMarioSet();
        Assertions.assertFalse(marioSet4.contains(mario4));

        Mario mario5 = new Mario("M7", "Test Mario");
        marioApp.addMario(mario5);

        Set<Mario> marioSet5= marioApp.getMarioSet();
        Assertions.assertFalse(marioSet5.contains(mario5));
    }

    @Test
    void addUser() {

        User user = new User("4", "Test", "Test", "Test.Test@example.com");
        marioApp.addUser(user);

        Set<User> userSet = marioApp.getUserSet();
        Assertions.assertTrue(userSet.contains(user));

        User user1 = new User(null, "Test", "Test", "Test.Test@example.com");
        marioApp.addUser(user1);

        Set<User> userSet1 = marioApp.getUserSet();
        Assertions.assertFalse(userSet1.contains(user1));

        User user2 = new User("5", null, "Test", "Test.Test@example.com");
        marioApp.addUser(user2);

        Set<User> userSet2 = marioApp.getUserSet();
        Assertions.assertFalse(userSet2.contains(user2));

        User user3 = new User("6", "Test", null, "Test.Test@example.com");
        marioApp.addUser(user3);

        Set<User> userSet3 = marioApp.getUserSet();
        Assertions.assertFalse(userSet3.contains(user3));

        User user4 = new User("7", "Test", "Test", null);
        marioApp.addUser(user4);

        Set<User> userSet4 = marioApp.getUserSet();
        Assertions.assertFalse(userSet4.contains(user4));

        User user5 = new User("8", "123", "Test", "Test.Test@example.com");
        marioApp.addUser(user5);

        Set<User> userSet5 = marioApp.getUserSet();
        Assertions.assertFalse(userSet5.contains(user5));

        User user6 = new User("9", "Test", "123", "Test.Test@example.com");
        marioApp.addUser(user6);

        Set<User> userSet6 = marioApp.getUserSet();
        Assertions.assertFalse(userSet6.contains(user6));

        User user7 = new User("1", "Test", "Test", "Test.Test@example.com");
        marioApp.addUser(user7);

        Set<User> userSet7= marioApp.getUserSet();
        Assertions.assertFalse(userSet7.contains(user7));

        User user8 = new User("10", "Test", "Test", "Test.Test@example.com");
        marioApp.addUser(user8);

        Set<User> userSet8= marioApp.getUserSet();
        Assertions.assertFalse(userSet8.contains(user8));

        User user9 = new User("9", "Test", "Test", "Test.Testexample.com");
        marioApp.addUser(user9);

        Set<User> userSet9 = marioApp.getUserSet();
        Assertions.assertFalse(userSet9.contains(user9));
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
        Assertions.assertTrue(sentMarios.size() > 0);
    }

    @Test
    void findSentMariosByRecipient() {
        String recipientId = "2";
        Set<SentMario> sentMarios = marioApp.findSentMariosByRecipient(recipientId);
        Assertions.assertNotNull(sentMarios);
        Assertions.assertEquals(1, sentMarios.size());
    }
}