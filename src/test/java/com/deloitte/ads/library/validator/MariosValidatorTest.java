package com.deloitte.ads.library.validator;

import com.deloitte.ads.library.repository.Mario;
import com.deloitte.ads.library.repository.MarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class MariosValidatorTest {

    @Mock
    private MarioRepository marioRepository;

    @InjectMocks
    private MariosValidator mariosValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsValidMariosValidMario() {
        Mario mario = new Mario();
        mario.setType("ThankYou");

        when(marioRepository.existsByType("ThankYou")).thenReturn(false);

        assertTrue(mariosValidator.isValidMarios(mario));
    }

    @Test
    void testIsValidMariosNullMario() {
        assertFalse(mariosValidator.isValidMarios(null));
    }

    @Test
    void testIsValidMariosEmptyType() {
        Mario mario = new Mario();
        mario.setType("");

        assertFalse(mariosValidator.isValidMarios(mario));
    }

    @Test
    void testIsValidMariosDuplicateType() {
        Mario mario = new Mario();
        mario.setType("ThankYou");

        when(marioRepository.existsByType("ThankYou")).thenReturn(true);

        assertFalse(mariosValidator.isValidMarios(mario));
    }
}
