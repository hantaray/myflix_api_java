package com.hantaray.myflixAPI;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    // Autowired instantiates class
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }
    public Optional<Movie> singleMovie(ObjectId id){
        return movieRepository.findById(id);
    }
    public Optional<Director> getDirectorByName(String directorName) {
        Optional<Movie> movie = movieRepository.findMovieByDirector_Name(directorName);
        return movie.map(Movie::getDirector);
    }
}
