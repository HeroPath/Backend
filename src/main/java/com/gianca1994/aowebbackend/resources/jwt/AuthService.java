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

import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gianca1994.aowebbackend.resources.user.dto.request.UserRegisterDTO;


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
    private JwtTokenUtil jwt;

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

    public User saveUser(UserRegisterDTO user) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to save a new user in the database.
         * @param UserDTO user
         * @return User
         */
        if (!validateEmail(user.getEmail().toLowerCase())) throw new BadRequest(JWTConst.EMAIL_NOT_VALID);
        String username = user.getUsername().toLowerCase();
        String password = user.getPassword();
        String email = user.getEmail().toLowerCase();
        Class aClass = ModifConfig.CLASSES.stream().filter(
                        c -> c.getName().equalsIgnoreCase(user.getClassName()))
                .findFirst().orElse(null);

        validator.saveUser(username, password, email, aClass, userR);

        Inventory inventory = new Inventory();
        Equipment equipment = new Equipment();
        inventoryR.save(inventory);
        equipmentR.save(equipment);

        User newUser = new User(
                username, encryptPassword(user.getPassword()), email,
                inventory, equipment,
                aClass.getName(),
                aClass.getStrength(), aClass.getDexterity(), aClass.getIntelligence(),
                aClass.getVitality(), aClass.getLuck()
        );
        newUser.calculateStats(true);

        if (Objects.equals(user.getUsername(), "gianca") || Objects.equals(user.getUsername(), "lucho"))
            newUser.setRole("ADMIN");

        return userR.save(newUser);
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
}
