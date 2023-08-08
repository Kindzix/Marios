package com.deloitte.ads.library.validator;

import com.deloitte.ads.library.repository.SentMario;
import com.deloitte.ads.library.repository.SentMarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SentMarioValidator {

    private final SentMarioRepository sentMarioRepository;

    @Autowired
    public SentMarioValidator(SentMarioRepository sentMarioRepository) {
        this.sentMarioRepository = sentMarioRepository;
    }

    public boolean isValidSentMario(SentMario sentMario) {
        if (sentMario == null) {
            return false;
        }

        if (sentMario.getMario() == null) {
            return false;
        }

        if (sentMario.getComment() == null) {
            sentMario.setComment("");
        }

        if (sentMario.getTheme() == null) {
            sentMario.setTheme("");
        }

        if(sentMario.getRecipientNames() == null) {
            return false;
        }

        return sentMario.getId() == null || !sentMarioRepository.existsById(sentMario.getId());
    }
}
