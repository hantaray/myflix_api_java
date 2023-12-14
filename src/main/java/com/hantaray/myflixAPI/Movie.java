package com.hantaray.myflixAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.List;

@Document(collection = "movies")
// creates getter and setter
@Data
// creates constructors
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private List<Object> genres;
    private String genre_name;
    private String genre_description;
    private Object director;
    private String director_name;
    private String director_bio;
    private String image_path;
    private Boolean featured;

}
