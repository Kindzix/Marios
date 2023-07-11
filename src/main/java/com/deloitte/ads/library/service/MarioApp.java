package com.deloitte.ads.library.service;

import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.repository.SentMario;
import com.google.common.collect.Sets;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarioApp {

    private Set<SentMario> sentMarios;
    private final Set<Mario> marios;
    private final Set<User> users;

    public MarioApp() {

        User user1 = new User("1", "Olga", "Przybysz", "olga.przybysz@example.com");
        User user2 = new User("2", "Jagoda", "Rogala", "jagoda.rogala@example.com");
        User user3 = new User("3", "Norbert", "Michalak", "norbert.michalak@example.com");

        this.users = Sets.newHashSet(user1, user2, user3);

        Mario mario1 = new Mario("M1", "Wielki dzięki, za pomoc!");
        Mario mario2 = new Mario("M2", "Super wykonałeś to zadanie!");
        Mario mario3 = new Mario("M3", "Doskonale się spisałeś!");
        Mario mario4 = new Mario("M4", "Gratuluję, świetnie wykonanej pracy!");
        Mario mario5 = new Mario("M5", "Ułatwiło mi to dzień/pracę!");

        this.marios = Sets.newHashSet(mario1, mario2, mario3, mario4, mario5);


        SentMario sentMario1 = new SentMario(mario1, "Dobra robota!", "1", "2");
        SentMario sentMario2 = new SentMario(mario2, "Niesamowite osiągnięcie!", "1", "3");
        SentMario sentMario3 = new SentMario(mario3, "Wspaniała praca!", "2", "1");

        this.sentMarios = Sets.newHashSet(sentMario1, sentMario2, sentMario3);
        sendMario(sentMario1);
        sendMario(sentMario2);
        sendMario(sentMario3);
    }

    public void addMario(Mario mario) {
        marios.add(mario);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Mario> getMarioList() {
        return new ArrayList<>(marios);
    }

    public List<User> getUserList() {
        return new ArrayList<>(users);
    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getIdUser().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public void displaySentMarios(String userId) {
        User user = getUserById(userId);
        if (user == null) {
            System.out.println("Użytkownik o podanym identyfikatorze nie istnieje.");
            return;
        }

        System.out.println("Wystawione kudosy przez użytkownika: " + user.getFullName());
        List<SentMario> sentMariosByUser = findSentMariosBySender(userId);
        if (sentMariosByUser.isEmpty()) {
            System.out.println("Brak wystawionych kudosów.");
        } else {
            for (SentMario sentMario : sentMariosByUser) {
                System.out.println("Mario ID: " + sentMario.getMario().getIdMarios());
                System.out.println("Rodzaj Mario: " + sentMario.getMario().getType());
                System.out.println("Komentarz: " + sentMario.getComment());
                System.out.println("Odbiorca: " + getUserById(sentMario.getRecipientId()).getFullName());
                System.out.println();
            }
        }
    }

    public void displayReceivedMarios(String userId) {
        User user = getUserById(userId);
        if (user == null) {
            System.out.println("Użytkownik o podanym identyfikatorze nie istnieje.");
            return;
        }

        System.out.println("Otrzymane kudosy przez użytkownika: " + user.getFullName());
        List<SentMario> receivedMarios = findSentMariosByRecipient(userId);
        if (receivedMarios.isEmpty()) {
            System.out.println("Brak otrzymanych kudosów.");
        } else {
            for (SentMario sentMario : receivedMarios) {
                System.out.println("Mario ID: " + sentMario.getMario().getIdMarios());
                System.out.println("Rodzaj Mario: " + sentMario.getMario().getType());
                System.out.println("Komentarz: " + sentMario.getComment());
                System.out.println();
            }
        }
    }

    public void sendMario(SentMario sentMario) {
        sentMarios.add(sentMario);

        User recipient = getUserById(sentMario.getRecipientId());
        if (recipient != null) {
            recipient.addMario(sentMario.getMario().getType(), sentMario.getComment());
        }
    }

    public List<SentMario> findSentMariosBySender(String senderId) {
        List<SentMario> sentMariosBySender = new ArrayList<>();
        for (SentMario sentMario : sentMarios) {
            if (sentMario.getSenderId().equals(senderId)) {
                sentMariosBySender.add(sentMario);
            }
        }
        return sentMariosBySender;
    }

    public List<SentMario> findSentMariosByRecipient(String recipientId) {
        List<SentMario> sentMariosByRecipient = new ArrayList<>();
        for (SentMario sentMario : sentMarios) {
            if (sentMario.getRecipientId().equals(recipientId)) {
                sentMariosByRecipient.add(sentMario);
            }
        }
        return sentMariosByRecipient;
    }
}