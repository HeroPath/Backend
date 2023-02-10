package com.gianca1994.aowebbackend.resources.jwt.utilities;

import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.user.UserRepository;

public class AuthServiceValidator {

    public void saveUser(String username, String email, String password, Class aClass, UserRepository userRepository) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to save the user.
         * @param String username
         * @param String email
         * @param String password
         * @param Class aClass
         * @param UserRepository userRepository
         * @return void
         */
        if (!username.matches(JWTConst.USERNAME_PATTERN)) throw new BadRequest(JWTConst.USERNAME_NOT_VALID);
        if (userRepository.existsByUsername(username)) throw new Conflict(JWTConst.USERNAME_EXISTS);
        if (userRepository.existsByEmail(email)) throw new Conflict(JWTConst.EMAIL_EXISTS);
        if (username.length() < 3 || username.length() > 20) throw new BadRequest(JWTConst.USERNAME_LENGTH);
        if (password.length() < 4 || password.length() > 20) throw new BadRequest(JWTConst.PASSWORD_LENGTH);
        if (aClass == null) throw new BadRequest(JWTConst.CLASS_NOT_FOUND);
    }
}
