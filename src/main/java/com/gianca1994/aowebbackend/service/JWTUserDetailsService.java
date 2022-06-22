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
        if (userRepository.findByUsername(user.getUsername()) != null) throw new ConflictException("Username already exists");

        Role standardRole = roleRepository.findById(1L).get();
        Class aClass = classRepository.findById(user.getClassId()).get();

        User newUser = new User(
                user.getUsername(), encryptPassword(user.getPassword()),
                user.getEmail(),
                standardRole,
                standardRole.getRoleName(),
                aClass,
                aClass.getName(),
                (short) 1, 0L, 100L,
                1000L, 0,
                15L, 10L,
                1000, 1000,
                5 + aClass.getStrength(),
                5 + aClass.getDexterity(),
                5 + aClass.getIntelligence(),
                5 + aClass.getVitality(),
                5 + aClass.getLuck(),
                0
        );
        return userRepository.save(newUser);
    }

}
