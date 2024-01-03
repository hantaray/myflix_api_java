package com.hantaray.myflixAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> allUser(){
        return userRepository.findAll();
    }
    public Optional<User> singleUser(String username){
        return userRepository.findUserByUsername(username);
    }
    public User addUser(String username, String password, String email, Date birthday) {
        return userRepository.insert(new User(username, password, email, birthday, new ArrayList<>()));
    }
    public void deleteUserByName(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);

        user.ifPresent(u -> userRepository.deleteById(u.getId()));
    }
}
