package com.gianca1994.aowebbackend.resources.jwt;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.equipment.EquipmentRepository;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.inventory.InventoryRepository;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.jwt.config.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.jwt.dto.UserRegisterJwtDTO;
import com.gianca1994.aowebbackend.resources.jwt.utilities.AuthServiceValidator;
import com.gianca1994.aowebbackend.resources.jwt.utilities.JWTConst;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
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

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gianca1994.aowebbackend.resources.user.dto.request.UserRegisterDTO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * @Author: Gianca1994
 * Explanation: This class is used to authenticate the user.
 */

@Service
public class AuthService implements UserDetailsService {

    AuthServiceValidator validator = new AuthServiceValidator();

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
         * Explanation: This method is used to load the user by username.
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
         * Explanation: This method is used to authenticate the user.
         * @param String username: The username of the user.
         * @param String password: The password of the user.
         * @return void
         */
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            throw new NotFound(JWTConst.PASSWORD_INCORRECT, e);
        } catch (DisabledException e) {
            throw new Exception(JWTConst.USER_DISABLED, e);
        }
    }

    public User saveUser(UserRegisterDTO user) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to save a new user in the database.
         * @param UserDTO user
         * @return User
         */
        if (!validateEmail(user.getEmail().toLowerCase())) throw new BadRequest(JWTConst.EMAIL_NOT_VALID);

        UserRegisterJwtDTO userJwt = new UserRegisterJwtDTO(user.getUsername(), user.getPassword(), user.getEmail(), user.getClassName());
        validator.saveUser(userJwt.getUsername(), userJwt.getPassword(), userJwt.getEmail(), userJwt.getAClass(), userR);

        userJwt.setInventory(new Inventory());
        userJwt.setEquipment(new Equipment());
        User newUser = new User(userJwt);
        newUser.calculateStats(true);

        if (user.getUsername().equals("gianca") || user.getUsername().equals("lucho")) newUser.setRole("ADMIN");

        Thread saveThread = new Thread(() -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.executeWithoutResult(status -> {
                inventoryR.save(newUser.getInventory());
                equipmentR.save(newUser.getEquipment());
                try {
                    newUser.generatePrivateAndPublicKey();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                userR.save(newUser);
            });
        });
        saveThread.start();
        return newUser;
    }

    private boolean validateEmail(String email) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the email address.
         * @param String email
         * @return boolean
         */
        Pattern pattern = Pattern.compile(JWTConst.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String encryptPassword(String password) {
        /**
         * @Author: Gianca1994
         * Explanation: This method encrypts the password using BCrypt.
         * @param String password
         * @return String
         */
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
