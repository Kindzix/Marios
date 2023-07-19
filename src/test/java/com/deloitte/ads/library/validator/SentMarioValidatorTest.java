package com.deloitte.ads.library.validator;

import com.deloitte.ads.library.repository.SentMario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SentMarioValidatorTest {

    @InjectMocks
    private SentMarioValidator sentMarioValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsValidSentMarioNullSentMario() {
        assertFalse(sentMarioValidator.isValidSentMario(null));
    }

    @Test
    void testIsValidSentMarioNullMario() {
        SentMario sentMario = new SentMario();
        assertFalse(sentMarioValidator.isValidSentMario(sentMario));
    }
}
