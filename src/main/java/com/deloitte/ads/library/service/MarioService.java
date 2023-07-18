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
;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.deloitte.ads.library.repository.UserRole.USER;

@Service
public class MarioService {

    private static final Logger logger = Logger.getLogger(MarioService.class.getName());

    private  MarioRepository marioRepository;
    private  SentMarioRepository sentMarioRepository;
    private  UserRepository userRepository;

    private  MariosValidator mariosValidator;
    private  UserValidator userValidator;
    private  SentMarioValidator sentMarioValidator;

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
        } else{
            logger.log(Level.WARNING, "Mario: {0}", mario.getType());
            throw new ResponseStatusException(HttpStatus.IM_USED, "User: {0} " + mario + " bad request.");
        }
    }

    public void addUser(User user) {
        if (userValidator.isValidUser(user)) {
            userRepository.save(user);
            logger.log(Level.INFO, "User added: {0}", user);
        } else {
            logger.log(Level.WARNING, "User: {0}", user);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User: {0} " + user + " bad request.");
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
        if (sentMarioValidator.isValidSentMario(sentMario)) {
            Optional<Mario> marioOptional = marioRepository.findById(sentMario.getMario().getIdMarios());
            if (marioOptional.isEmpty()) {
                logger.log(Level.WARNING, "Mario with ID {0} not found.", sentMario.getMario().getIdMarios());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mario with ID " + sentMario.getMario().getIdMarios() + " not found.");
            }

            User sender = userRepository.findByEmail(sentMario.getSender().getEmail());
            if (sender == null) {
                logger.log(Level.WARNING, "User with email {0} not found.", sentMario.getSender().getEmail());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email " + sentMario.getSender().getEmail() + " not found.");
            }

            sentMarioRepository.save(sentMario);

            for (User recipient : sentMario.getRecipients()) {
                recipient.getReceivedMarios().add(sentMario);
                userRepository.save(recipient);
            }

            logger.log(Level.INFO, "Marios sent: {0}", sentMario);
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
        SentMario sentMario3 = new SentMario(mario2, "Super", user2, Sets.newHashSet(user1, user3));

        sendMarios(sentMario1);
        sendMarios(sentMario2);
        sendMarios(sentMario3);
    }
}
