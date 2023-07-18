package com.deloitte.ads.library.service;

import com.deloitte.ads.library.validator.MariosValidator;
import com.deloitte.ads.library.validator.SentMarioValidator;
import com.deloitte.ads.library.repository.*;
import com.deloitte.ads.library.validator.UserValidator;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.deloitte.ads.library.repository.UserRole.USER;

@Service
public class MarioService {

    private final MarioRepository marioRepository;
    private final SentMarioRepository sentMarioRepository;
    private final UserRepository userRepository;

    private final MariosValidator mariosValidator;
    private final UserValidator userValidator;
    private final SentMarioValidator sentMarioValidator;

    @Autowired
    public MarioService(MarioRepository marioRepository, SentMarioRepository sentMarioRepository, UserRepository userRepository, MariosValidator mariosValidator, UserValidator userValidator, SentMarioValidator sentMarioValidator) {
        this.marioRepository = marioRepository;
        this.sentMarioRepository = sentMarioRepository;
        this.userRepository = userRepository;
        this.mariosValidator = mariosValidator;
        this.userValidator = userValidator;
        this.sentMarioValidator = sentMarioValidator;
    }

    public void addMario(Mario mario) {
        if (mariosValidator.isValidMarios(mario)) {
            marioRepository.save(mario);
        }
    }

    public void addUser(User user) {
        if (userValidator.isValidUser(user)) {
            userRepository.save(user);
        }
    }

    public Set<User> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Mario> getMarioSet() {
        return StreamSupport.stream(marioRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    public Mario getMarioById(Long marioId) {
        Optional<Mario> optionalMario = marioRepository.findById(marioId);
        return optionalMario.orElse(null);
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    public Set<SentMario> findSentMariosByRecipientEmail(String recipientEmail) {
        return sentMarioRepository.findByRecipientsEmail(recipientEmail);
    }

    public Set<SentMario> findSentMariosBySenderEmail(String senderEmail) {
        return sentMarioRepository.findBySenderEmail(senderEmail);
    }

    public void sendMarios(SentMario sentMario) {
        if (sentMarioValidator.isValidSentMario(sentMario)){
                Optional<Mario> marioOptional = marioRepository.findById(sentMario.getMario().getIdMarios());
            if (marioOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mario with ID " + sentMario.getMario().getIdMarios() + " not found.");
            }

            User sender = userRepository.findByEmail(sentMario.getSender().getEmail());
            if (sender == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email " + sentMario.getSender().getEmail() + " not found.");
            }
            sentMarioRepository.save(sentMario);

            for (User recipient : sentMario.getRecipients()) {
                recipient.getReceivedMarios().add(sentMario);
                userRepository.save(recipient);
            }
    }
    }


    public void initializeMarios() {
        Mario mario1 = new Mario("Wielki dzięki, za pomoc!");
        Mario mario2 = new Mario("Super wykonałeś to zadanie!");
        Mario mario3 = new Mario("Doskonale się spisałeś!");
        Mario mario4 = new Mario("Gratuluję, świetnie wykonanej pracy!");
        Mario mario5 = new Mario("Ułatwiło mi to dzień/pracę!");

        addMario(mario1);
        addMario(mario2);
        addMario(mario3);
        addMario(mario4);
        addMario(mario5);

        User user1 = new User("Olga", "Przybysz", "olga.przybysz@example.com", USER);
        User user2 = new User("Jagoda", "Rogala", "jagoda.rogala@example.com", USER);
        User user3 = new User("Norbert", "Michalak", "norbert.michalak@example.com", USER);

        addUser(user1);
        addUser(user2);
        addUser(user3);

        SentMario sentMario1 = new SentMario(mario1 , "", user3, Sets.newHashSet(user2));
        SentMario sentMario2 = new SentMario(mario2, "Bardzo pomogło!", user1, Sets.newHashSet(user2, user3));

        sendMarios(sentMario1);
        sendMarios(sentMario2);

    }

}
