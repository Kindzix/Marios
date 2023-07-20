package com.deloitte.ads.library.service;

import com.deloitte.ads.library.repository.*;
import com.deloitte.ads.library.validator.MariosValidator;
import com.deloitte.ads.library.validator.SentMarioValidator;
import com.deloitte.ads.library.validator.UserValidator;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.deloitte.ads.library.repository.UserRole.USER;


@Service
public class MarioService {

    private static final Logger logger = Logger.getLogger(MarioService.class.getName());

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
            logger.log(Level.INFO, "Mario added: {0}", mario);
        } else {
            logger.log(Level.WARNING, "Cannot add Mario: {0}", mario.getType());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add: {0} " + mario + " bad request.");
        }
    }


    public void addUser(User user) {
        if (userValidator.isValidUser(user)) {
            userRepository.save(user);
            logger.log(Level.INFO, "User added: {0}", user);
        } else {
            logger.log(Level.WARNING, "Cannot add user: {0}", user);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add User: {0} " + user + " bad request.");
        }
    }

    public void addUserFromUserRequest(UserRequest userRequest) {
        User user = User.fromUserRequest(userRequest);
        addUser(user);
    }

    public Set<User> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    public Set<Mario> getAllMarios() {
        return StreamSupport.stream(marioRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    public Mario getMarioByUuid(UUID marioUuid) {
        Optional<Mario> optionalMario = Optional.ofNullable(marioRepository.findByUuid(marioUuid));
        return optionalMario.orElse(null);
    }

    public User getUserByUuid(UUID userUuid) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUuid(userUuid));
        return optionalUser.orElse(null);
    }

    public Set<SentMario> findSentMariosByRecipientUuid(UUID recipientUuid) {
        User recipient = getUserByUuid(recipientUuid);
        if (recipient == null) {
            return new HashSet<>();
        }
        return sentMarioRepository.findByRecipientsContains(recipient);
    }

    public Set<SentMario> findSentMariosBySenderUuid(UUID senderUuid) {
        User sender = getUserByUuid(senderUuid);
        if (sender == null) {
            return new HashSet<>();
        }
        return sentMarioRepository.findBySender(sender);
    }

    public void sendMarios(SentMario sentMario) {
        if (sentMarioValidator.isValidSentMario(sentMario)) {
            marioRepository.save(sentMario.getMario());
            userRepository.save(sentMario.getSender());
            userRepository.saveAll(sentMario.getRecipients());
            sentMarioRepository.save(sentMario);
            logger.log(Level.INFO, "Marios sent: {0}", sentMario);
        }
    }

    public void sendMariosFromSendMarioRequest(SendMarioRequest sentMarioRequest) {
        UUID marioUuid = UUID.fromString(sentMarioRequest.getMarioUuid());
        Mario mario = getMarioByUuid(marioUuid);
        if (mario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mario with UUID " + marioUuid + " not found.");
        }

        UUID senderUuid = UUID.fromString(sentMarioRequest.getSenderUuid());
        User sender = getUserByUuid(senderUuid);
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender with UUID " + senderUuid + " not found.");
        }

        Set<UUID> recipientUuids = sentMarioRequest.getRecipientUuids().stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        Set<User> recipients = recipientUuids.stream()
                .map(this::getUserByUuid)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        SentMario sentMario = sentMarioRequest.toSentMario(mario, sender, recipients, sentMarioRequest.getComment());
        sendMarios(sentMario);
    }

    public void addMarioFromMarioRequest(MarioRequest marioRequest) {
        Mario mario = new Mario(marioRequest.getType());
        addMario(mario);
    }

    public void initializeMarios() {
//        Mario mario1 = new Mario("Wielki dzięki, za pomoc!");
//        Mario mario2 = new Mario("Super wykonałeś to zadanie!");
//        Mario mario3 = new Mario("Doskonale się spisałeś!");
//        Mario mario4 = new Mario("Gratuluję, świetnie wykonanej pracy!");
//        Mario mario5 = new Mario("Ułatwiło mi to dzień/pracę!");
//
//        addMario(mario1);
//        addMario(mario2);
//        addMario(mario3);
//        addMario(mario4);
//        addMario(mario5);
//
//        User user1 = new User("Olga", "Przybysz", "olga.przybysz@example.com", USER);
//        User user2 = new User("Jagoda", "Rogala", "jagoda.rogala@example.com", USER);
//        User user3 = new User("Norbert", "Michalak", "norbert.michalak@example.com", USER);
//
//        addUser(user1);
//        addUser(user2);
//        addUser(user3);
//
//        SentMario sentMario1 = new SentMario(mario1 , "", user3, Sets.newHashSet(user2));
//        SentMario sentMario2 = new SentMario(mario2, "Bardzo pomogło!", user1, Sets.newHashSet(user2, user3));
//        SentMario sentMario3 = new SentMario(mario2, "Super", user2, Sets.newHashSet(user1, user3));
//
//        sendMarios(sentMario1);
//        sendMarios(sentMario2);
//        sendMarios(sentMario3);
    }
}
