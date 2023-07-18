package com.deloitte.ads.library.validator;

import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.MarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MariosValidator {

    private final MarioRepository marioRepository;

    @Autowired
    public MariosValidator(MarioRepository marioRepository) {
        this.marioRepository = marioRepository;
    }

    public boolean isValidMarios(Mario mario) {
        if (mario == null) {
            return false;
        }

        if (mario.getType() == null || mario.getType().isEmpty()) {
            return false;
        }

        return !marioRepository.existsByType(mario.getType());
    }
}
