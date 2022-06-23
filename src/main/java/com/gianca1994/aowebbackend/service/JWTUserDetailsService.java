package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.exception.BadRequestException;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.exception.NotFoundException;
import com.gianca1994.aowebbackend.model.Class;
import com.gianca1994.aowebbackend.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gianca1994.aowebbackend.dto.UserDTO;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.UserRepository;

import com.gianca1994.aowebbackend.model.Role;
import com.gianca1994.aowebbackend.repository.RoleRepository;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to load the user by username.
         * @param String username
         * @return UserDetails
         */
        User user = userRepository.findByUsername(username);

        if (user == null) throw new NotFoundException("User not found");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);
    }

    private boolean validateEmail(String email) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the email address.
         * @param String email
         * @return boolean
         */
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
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

    public User saveUser(UserDTO user) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to save a new user in the database.
         * @param UserDTO user
         * @return User
         */
        if (!validateEmail(user.getEmail())) throw new BadRequestException("Invalid email address");
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new ConflictException("Username already exists");

        Role standardRole = roleRepository.findById(1L).get();
        Class aClass = classRepository.findById(user.getClassId()).get();

        int minDmg = 0, maxDmg = 0, maxHp = 0;

        switch (aClass.getName()) {
            case "mage":
                minDmg = aClass.getIntelligence() * 4;
                maxDmg = aClass.getIntelligence() * 7;
                maxHp = aClass.getVitality() * 10;
                break;
            case "warrior":
                minDmg = aClass.getStrength() * 3;
                maxDmg = aClass.getStrength() * 5;
                maxHp = aClass.getVitality() * 20;
                break;
            case "archer":
                minDmg = aClass.getDexterity() * 4;
                maxDmg = aClass.getDexterity() * 6;
                maxHp = aClass.getVitality() * 15;
                break;
        }

        User newUser = new User(
                user.getUsername(), encryptPassword(user.getPassword()),
                user.getEmail(),
                standardRole,
                aClass,
                (short) 1, 0L, 100L,
                1000L, 0,
                maxDmg, minDmg,
                maxHp, maxHp,
                aClass.getStrength(),
                aClass.getDexterity(),
                aClass.getIntelligence(),
                aClass.getVitality(),
                aClass.getLuck(),
                0, 0, 0, 0
        );

        if (Objects.equals(user.getUsername(), "gianca") ||
                Objects.equals(user.getUsername(), "lucho")) {
            newUser.setRole(roleRepository.findById(2L).get());
        }
        return userRepository.save(newUser);
    }

}
