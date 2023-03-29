package com.gianca1994.heropathbackend.resources.jwt.utilities;

import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.classes.Class;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import com.gianca1994.heropathbackend.utils.Const;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to validate the user.
 */

public class AuthServiceValidator {

    public void saveUser(String username, String password, String email, Class aClass, UserRepository userR) throws Conflict {
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
        if (!username.matches(Const.JWT.USER_PATTERN.getMsg())) throw new BadReq(Const.JWT.USER_NOT_VALID.getMsg());
        if (userR.existsByUsername(username)) throw new Conflict(Const.JWT.USER_EXISTS.getMsg());
        if (userR.existsByEmail(email)) throw new Conflict(Const.JWT.EMAIL_EXISTS.getMsg());
        if (username.length() < 3 || username.length() > 20) throw new BadReq(Const.JWT.USER_LENGTH.getMsg());
        if (password.length() < 3 || password.length() > 20) throw new BadReq(Const.JWT.PASS_LENGTH.getMsg());
        if (aClass == null) throw new BadReq(Const.JWT.CLASS_NOT_FOUND.getMsg());
    }
}
