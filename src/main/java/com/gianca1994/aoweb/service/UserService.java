package com.gianca1994.aoweb.service;

import java.util.ArrayList;

import com.gianca1994.aoweb.model.User;
import com.gianca1994.aoweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getProfile(String username) {
        return userRepository.findByUsername(username);
    }


    public ArrayList<User> getRankingAll() {
        return (ArrayList<User>) userRepository.findAll();
    }
}
