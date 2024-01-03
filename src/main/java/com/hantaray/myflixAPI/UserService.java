package com.hantaray.myflixAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieService movieService;
    public List<User> allUser(){
        return userRepository.findAll();
    }
    public Optional<User> singleUser(String username){
        return userRepository.findUserByUsername(username);
    }
    public ResponseEntity<String> addUser(String username, String password, String email, Date birthday) {
        // Check if the username already exists
        if (usernameExists(username)) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.insert(new User(username, password, email, birthday, new ArrayList<>()));
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    public boolean usernameExists(String username) {
        // Check if a user with the given username already exists
        return userRepository.findUserByUsername(username).isPresent();
    }
    public void deleteUserByName(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);

        user.ifPresent(u -> userRepository.deleteById(u.getId()));
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public ResponseEntity<String> addMovieToFavorite(String username, String movieName) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Movie> optionalMovie = movieService.findMovieByName(movieName);

            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();
                List<Movie> favoriteMovies = user.getFavoriteMovies();

                // Check if the movie is not already in favorites
                if (!favoriteMovies.contains(movie)) {
                    favoriteMovies.add(movie);
                    userRepository.save(user);
                    return new ResponseEntity<>("Movie added to favorites", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Movie is already in favorites", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
