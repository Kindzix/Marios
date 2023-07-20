package com.deloitte.ads.library.validator;

import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testIsValidUserValidUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(false);

        assertTrue(userValidator.isValidUser(user));
    }

    @Test
     void testIsValidUserNullUser() {
        assertFalse(userValidator.isValidUser(null));
    }

    @Test
     void testIsValidUserInvalidEmailFormat() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe.com");

        assertFalse(userValidator.isValidUser(user));
    }
    @Test
     void testIsValidUserDuplicateEmail() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        assertFalse(userValidator.isValidUser(user));
    }
}
