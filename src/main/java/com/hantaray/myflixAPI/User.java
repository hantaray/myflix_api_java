package com.hantaray.myflixAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
// creates setter and setter
@Data
// creates constructors
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String name;
    private String password;
    private String email;
    private Date birthday;
    private List<ObjectId> favoriteMovies;
}
