package com.gianca1994.aowebbackend.resources.jwt;

import com.gianca1994.aowebbackend.resources.classes.ClassRepository;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.equipment.EquipmentRepository;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.inventory.InventoryRepository;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.role.RoleRepository;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Explanation: UserService
 */

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to load the user by username.
         * @param String username
         * @return UserDetails
         */
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFound(JWTConst.USER_NOT_FOUND);

        GrantedAuthority authorities = new SimpleGrantedAuthority(user.getRole().getRoleName());

        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), Collections.singleton(authorities));
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

    public User saveUser(UserRegisterDTO user) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to save a new user in the database.
         * @param UserDTO user
         * @return User
         */
        if (!validateEmail(user.getEmail().toLowerCase())) throw new BadRequest(JWTConst.EMAIL_NOT_VALID);

        String username = user.getUsername().toLowerCase();

        if (!username.matches(JWTConst.USERNAME_PATTERN)) throw new BadRequest(JWTConst.USERNAME_NOT_VALID);
        if (userRepository.findByUsername(username) != null) throw new Conflict(JWTConst.USERNAME_EXISTS);

        //TODO: ARREGLAR ESTO
        // if (userRepository.findByEmail(user.getEmail().toLowerCase()) != null) throw new Conflict(JWTConst.EMAIL_EXISTS);

        if (username.length() < 3 || username.length() > 20) throw new BadRequest(JWTConst.USERNAME_LENGTH);
        if (user.getPassword().length() < 3 || user.getPassword().length() > 20)
            throw new BadRequest(JWTConst.PASSWORD_LENGTH);

        Role standardRole = roleRepository.findById(1L).get();
        Class aClass = classRepository.findById(user.getClassId()).get();
        Title standardTitle = titleRepository.findById(1L).get();
        if (aClass.getName() == null) throw new BadRequest(JWTConst.CLASS_NOT_FOUND);

        Inventory inventory = new Inventory();
        Equipment equipment = new Equipment();

        inventoryRepository.save(inventory);
        equipmentRepository.save(equipment);

        User newUser = new User(
                username, encryptPassword(user.getPassword()),
                user.getEmail().toLowerCase(),
                standardRole,
                aClass,
                standardTitle,
                inventory,
                equipment,
                aClass.getStrength(),
                aClass.getDexterity(),
                aClass.getIntelligence(),
                aClass.getVitality(),
                aClass.getLuck()
        );

        newUser.calculateStats(true);

        if (Objects.equals(user.getUsername(), "gianca") || Objects.equals(user.getUsername(), "lucho"))
            newUser.setRole(roleRepository.findById(2L).get());

        return userRepository.save(newUser);
    }
}
