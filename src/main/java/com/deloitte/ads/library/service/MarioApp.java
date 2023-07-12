package com.deloitte.ads.library.service;

import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.repository.SentMario;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarioApp {

    private Set<SentMario> sentMarios;
    private Set<Mario> marios;
    private Set<User> users;

    public MarioApp() {
        this.marios = new HashSet<>();
        this.users = new HashSet<>();
        this.sentMarios = new HashSet<>();

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


        SentMario sentMario1 = new SentMario(mario1, "", "1", Sets.newHashSet(user2));
        SentMario sentMario2 = new SentMario(mario2, "Niesamowite osiągnięcie!", "1", Sets.newHashSet(user3, user2));
        SentMario sentMario3 = new SentMario(mario3, "Wspaniała praca!", "2", Sets.newHashSet(user1));

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

    public Set<Mario> getMarioSet() {
        return new HashSet<>(marios);
    }

    public Set<User> getUserSet() {
        return new HashSet<>(users);
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
        Set<SentMario> sentMariosByUser = findSentMariosBySender(userId);
        if (sentMariosByUser.isEmpty()) {
            System.out.println("Brak wystawionych kudosów.");
        } else {
            for (SentMario sentMario : sentMariosByUser) {
                System.out.println("Mario ID: " + sentMario.getMario().getIdMarios());
                System.out.println("Rodzaj Mario: " + sentMario.getMario().getType());
                System.out.println("Komentarz: " + sentMario.getComment());
                System.out.println("Odbiorca: " + getRecipientsNames(sentMario.getRecipients()));
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
        Set<SentMario> receivedMarios = findSentMariosByRecipient(userId);
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

        for (User recipient : sentMario.getRecipients()) {
            if (users.contains(recipient)) {
                recipient.addReceivedMario(sentMario.getMario().getType(), sentMario.getComment());
            }
        }
    }

    public Set<SentMario> findSentMariosBySender(String senderId) {
        Set<SentMario> sentMariosBySender = new HashSet<>();
        for (SentMario sentMario : sentMarios) {
            if (sentMario.getSenderId().equals(senderId)) {
                sentMariosBySender.add(sentMario);
            }
        }
        return sentMariosBySender;
    }

    public Set<SentMario> findSentMariosByRecipient(String recipientId) {
        Set<SentMario> sentMariosByRecipient = new HashSet<>();
        for (SentMario sentMario : sentMarios) {
            for (User recipient : sentMario.getRecipients()) {
                if (recipient.getIdUser().equals(recipientId)) {
                    sentMariosByRecipient.add(sentMario);
                    break;
                }
            }
        }
        return sentMariosByRecipient;
    }

    private String getRecipientsNames(Set<User> recipients) {
        StringBuilder names = new StringBuilder();
        for (User recipient : recipients) {
            names.append(recipient.getFullName()).append(", ");
        }
        if (names.length() > 0) {
            names.delete(names.length() - 2, names.length());
        }
        return names.toString();
    }
}
