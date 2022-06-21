package com.gianca1994.aowebbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public User saveUser(UserDTO user) {
        if (validateEmail(user.getEmail())) {
            Role standardRole = roleRepository.findById(1L).get();
            User newUser = new User(
                    user.getUsername(), encryptPassword(user.getPassword()),
                    user.getEmail(),
                    standardRole,
                    (short) 1, 0L, 100L,
                    1000L, 0,
                    15L, 10L,
                    1000, 1000,
                    5, 5, 5, 5, 5,
                    0
            );
            return userRepository.save(newUser);
        }
        return null;
    }
}