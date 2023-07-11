package com.deloitte.ads;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class MarioAppTest {

    private MarioApp marioApp;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        marioApp = new MarioApp();
        user1 = new User("1", "Olga", "Przybysz", "olga.przybysz@example.com");
        user2 = new User("2", "Jagoda", "Rogala", "jagoda.rogala@example.com");
        user3 = new User("3", "Norbert", "Michalak", "norbert.michalak@example.com");
        marioApp.addUser(user1);
        marioApp.addUser(user2);
        marioApp.addUser(user3);
    }

    @Test
    public void testAddMario() {
        Mario mario = new Mario("1", user1, Set.of(user2), "Wielki dzięki, za pomoc!");

        marioApp.addMario(mario);

        List<Mario> marioList = marioApp.getMarioList();
        Assertions.assertEquals(1, marioList.size());
        Assertions.assertEquals(mario, marioList.get(0));
    }

    @Test
    public void testAddMarioWithComment() {
        Mario mario = new Mario("1", user1, Set.of(user2), "Wielki dzięki, za pomoc!");
        String comment = "Bardzo dobrze wykonane zadanie!";

        marioApp.addMarioWithComment(mario, comment);

        List<Mario> marioList = marioApp.getMarioList();
        Assertions.assertEquals(1, marioList.size());
        Assertions.assertEquals(mario, marioList.get(0));
        Assertions.assertEquals(comment, user2.getReceivedMario().get(mario.getType()));
    }

    @Test
    public void testAddUser() {
        User user = new User("1", "Olga", "Przybysz", "olga.przybysz@example.com");

        marioApp.addUser(user);

        User retrievedUser = marioApp.getUserById("1");
        Assertions.assertEquals(user, retrievedUser);

    }


    @Test
    public void testDisplayUserMarios() {
        Mario mario1 = new Mario("1", user1, Set.of(user2), "Wielki dzięki, za pomoc!");
        Mario mario2 = new Mario("2", user2, Set.of(user1), "Doskonale się spisałeś!");

        marioApp.addMario(mario1);
        marioApp.addMarioWithComment(mario2, "ok");

        System.out.println();
        marioApp.displayUserMarios(user1);

        System.out.println();
        marioApp.displayUserMarios(user2);

        System.out.println();
        marioApp.displayUserMarios(user3);

    }
}