package com.gianca1994.heropathbackend.resources.jwt;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.equipment.Equipment;
import com.gianca1994.heropathbackend.resources.equipment.EquipmentRepository;
import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.inventory.InventoryRepository;
import com.gianca1994.heropathbackend.resources.jwt.dto.UserRegisterJwtDTO;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import com.gianca1994.heropathbackend.resources.user.dto.request.UserRegisterDTO;
import com.gianca1994.heropathbackend.utils.Const;
import com.gianca1994.heropathbackend.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author: Gianca1994
 * @Explanation: This class is used to authenticate the user.
 */

@Service
public class AuthService implements UserDetailsService {

    Validator validate = new Validator();

    @Autowired
    private UserRepository userR;

    @Autowired
    private InventoryRepository inventoryR;

    @Autowired
    private EquipmentRepository equipmentR;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFound {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to load the user by username.
         * @param String username
         * @return UserDetails
         */
        User user = userR.findByUsername(username);
        GrantedAuthority authorities = new SimpleGrantedAuthority(user.getRole());
        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), Collections.singleton(authorities));
    }

    public void authenticate(String username, String password) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to authenticate the user.
         * @param String username: The username of the user.
         * @param String password: The password of the user.
         * @return void
         */
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            throw new NotFound(Const.JWT.PASS_INCORRECT.getMsg(), e);
        } catch (DisabledException e) {
            throw new Exception(Const.JWT.USER_DISABLED.getMsg(), e);
        }
    }

    public User saveUser(UserRegisterDTO user) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to save the user.
         * @param UserRegisterDTO user
         * @return User
         */
        if (!validateEmail(user.getEmail().toLowerCase())) throw new BadReq(Const.JWT.EMAIL_NOT_VALID.getMsg());

        UserRegisterJwtDTO userJwt = new UserRegisterJwtDTO(user.getUsername(), user.getPassword(), user.getEmail(), user.getClassName());
        validate.saveUser(userJwt.getUsername(), userJwt.getPassword(), userJwt.getEmail(), userJwt.getAClass(), userR);

        userJwt.setInventory(new Inventory());
        userJwt.setEquipment(new Equipment());
        User newUser = new User(userJwt);
        newUser.calculateStats(true);

        if (SvConfig.ADMIN_LIST.contains(user.getUsername())) newUser.setRole(SvConfig.ADMIN_ROLE);
        Thread saveThread = new Thread(() -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.executeWithoutResult(status -> {
                newUser.setPassword(encryptPassword(newUser.getPassword()));
                inventoryR.save(newUser.getInventory());
                equipmentR.save(newUser.getEquipment());
                userR.save(newUser);
            });
        });
        saveThread.start();
        return newUser;
    }

    private boolean validateEmail(String email) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to validate the email address.
         * @param String email
         * @return boolean
         */
        Pattern pattern = Pattern.compile(Const.JWT.EMAIL_PATTERN.getMsg());
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String encryptPassword(String password) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method encrypts the password using BCrypt.
         * @param String password
         * @return String
         */
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
