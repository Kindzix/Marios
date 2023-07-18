package com.deloitte.ads.library.validator;

import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.MarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class MariosValidator {

    private final MarioRepository marioRepository;
    private static final Logger logger = Logger.getLogger(MariosValidator.class.getName());
    @Autowired
    public MariosValidator(MarioRepository marioRepository) {

        this.marioRepository = marioRepository;
    }

    public boolean isValidMarios(Mario mario) {
        if (mario == null) {
            logger.log(Level.WARNING, "Mario is null");
            return false;
        }

        if (mario.getType() == null || mario.getType().isEmpty()) {
            logger.log(Level.WARNING, "Mario type is null");
            return false;
        }

        return !marioRepository.existsByType(mario.getType());
    }
}
