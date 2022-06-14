package com.gianca1994.aoweb.service;


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

import com.gianca1994.aoweb.dto.UserDTO;
import com.gianca1994.aoweb.model.User;
import com.gianca1994.aoweb.repository.UserRepository;

import com.gianca1994.aoweb.model.Role;
import com.gianca1994.aoweb.repository.RoleRepository;

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
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

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

    public User save(UserDTO user) {
        if (validateEmail(user.getEmail())) {
            User newUser = new User();
            Role standardRole = roleRepository.findById(1L).get();

            newUser.setUsername(user.getUsername());
            newUser.setPassword(encryptPassword(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.getRoles().add(standardRole);

            newUser.setLevel((short) 1);
            newUser.setExperience(0);
            newUser.setExperienceToNextLevel(100);

            newUser.setGold(0);
            newUser.setDiamond(0);

            newUser.setHp(100);
            newUser.setMp(100);

            newUser.setStrength(5);
            newUser.setDexterity(5);
            newUser.setIntelligence(5);
            newUser.setVitality(5);
            newUser.setLuck(5);

            return userRepository.save(newUser);
        }
        return null;
    }

}
