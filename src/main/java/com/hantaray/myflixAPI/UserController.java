package com.hantaray.myflixAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    // Autowired instantiates class
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.allUser(), HttpStatus.OK);
    }
    @GetMapping("/{username}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userService.singleUser(username), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody Map<String, Object> payload) {
        String username = payload.get("username").toString();
        String password = payload.get("password").toString();
        String email = payload.get("email").toString();

        // Parse the birthday String to LocalDate
        LocalDate birthday = LocalDate.parse(payload.get("birthday").toString());

        // Convert LocalDate to Date if needed
        Date birthdayDate = java.sql.Date.valueOf(birthday);

        return new ResponseEntity<>(userService.addUser(username, password, email, birthdayDate), HttpStatus.CREATED);
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        Optional<User> user = userService.singleUser(username);

        if (user.isPresent()) {
            userService.deleteUserByName(username);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
