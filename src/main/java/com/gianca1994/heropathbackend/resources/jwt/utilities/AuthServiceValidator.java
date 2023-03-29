package com.gianca1994.heropathbackend.resources.jwt.utilities;

import com.gianca1994.heropathbackend.exception.BadRequest;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.classes.Class;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import com.gianca1994.heropathbackend.utils.Constants;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to validate the user.
 */

public class AuthServiceValidator {

    public void saveUser(String username, String password, String email, Class aClass, UserRepository userRepository) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to save the user.
         * @param String username
         * @param String email
         * @param String password
         * @param Class aClass
         * @param UserRepository userRepository
         * @return void
         */
        if (!username.matches(JWTConst.USERNAME_PATTERN)) throw new BadRequest(Constants.JWT.USER_NOT_VALID.getMsg());
        if (userRepository.existsByUsername(username)) throw new Conflict(Constants.JWT.USER_EXISTS.getMsg());
        if (userRepository.existsByEmail(email)) throw new Conflict(Constants.JWT.EMAIL_EXISTS.getMsg());
        if (username.length() < 3 || username.length() > 20) throw new BadRequest(Constants.JWT.USER_LENGTH.getMsg());
        if (password.length() < 3 || password.length() > 20) throw new BadRequest(Constants.JWT.PASS_LENGTH.getMsg());
        if (aClass == null) throw new BadRequest(Constants.JWT.CLASS_NOT_FOUND.getMsg());
    }
}
