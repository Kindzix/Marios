package com.deloitte.ads.library.validator;

import com.deloitte.ads.library.repository.User;
import com.deloitte.ads.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidUser(User user) {
        if (user == null) {
            return false;
        }

        if(!user.getEmail().endsWith("@example.com"))
        {
            return false;
        }

        if(userRepository.existsByEmail(user.getEmail()))
        {
            return false;
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty() ||
                user.getLastName() == null || user.getLastName().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getRole() == null) {
            return false;
        }

        return user.getFirstName().matches("^[a-zA-Z]+") && user.getLastName().matches("^[a-zA-Z]+");

    }
}
