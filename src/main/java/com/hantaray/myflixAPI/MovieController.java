package com.hantaray.myflixAPI;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {
    // Autowired instantiates class
    @Autowired
    private MovieService movieService;
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(id), HttpStatus.OK);
    }
    @GetMapping("/director/{directorName}")
    public ResponseEntity<Director> getDirector(@PathVariable String directorName) {
        Optional<Director> director = movieService.getDirectorByName(directorName);

        if (director.isPresent()) {
            return new ResponseEntity<>(director.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/genres/{genreName}")
    public ResponseEntity<Genre> getGenre(@PathVariable String genreName) {
        Optional<Movie> movie = movieService.getMovieByGenreName(genreName);

        if (movie.isPresent()) {
            Genre firstGenre = movie.get().getGenres().stream().findFirst().orElse(null);

            if (firstGenre != null) {
                return new ResponseEntity<>(firstGenre, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
