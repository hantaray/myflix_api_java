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
    public ResponseEntity<?> addUser(@RequestBody Map<String, Object> payload) {
        String username = payload.get("username").toString();
        String password = payload.get("password").toString();
        String email = payload.get("email").toString();

        // Parse the birthday String to LocalDate
        LocalDate birthday = LocalDate.parse(payload.get("birthday").toString());

        // Convert LocalDate to Date if needed
        Date birthdayDate = java.sql.Date.valueOf(birthday);

        ResponseEntity<String> response = userService.addUser(username, password, email, birthdayDate);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response.getBody(), HttpStatus.BAD_REQUEST);
        }
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
    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody Map<String, Object> updatedFields) {
        Optional<User> user = userService.singleUser(username);

        if (user.isPresent()) {
            User existingUser = user.get();

            // Update fields of the existing user with new values from the map
            for (Map.Entry<String, Object> entry : updatedFields.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                // Handle each field individually, update only if the value is not null
                switch (fieldName) {
                    case "username":
                        if (fieldValue != null) {
                            existingUser.setUsername(fieldValue.toString());
                        }
                        break;
                    case "password":
                        if (fieldValue != null) {
                            existingUser.setPassword(fieldValue.toString());
                        }
                        break;
                    case "email":
                        if (fieldValue != null) {
                            existingUser.setEmail(fieldValue.toString());
                        }
                        break;
                    default:
                        // Ignore unknown fields
                        break;
                }
            }
            // Save the updated user to the database
            User updatedUserEntity = userService.updateUser(existingUser);

            return new ResponseEntity<>(updatedUserEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{username}/movies/{movieName}")
    public ResponseEntity<String> addMovieToFavorite(@PathVariable String username, @PathVariable String movieName) {
        return userService.addMovieToFavorite(username, movieName);
    }
    @DeleteMapping("/{username}/movies/{movieName}")
    public ResponseEntity<?> removeMovieFromFavorites(@PathVariable String username, @PathVariable String movieName) {
        Optional<User> optionalUser = userService.removeMovieFromFavorites(username, movieName);

        if (optionalUser.isPresent()) {
            return new ResponseEntity<>("Movie removed from favorites.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }
}
