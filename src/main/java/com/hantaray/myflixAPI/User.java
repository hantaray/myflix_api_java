package com.hantaray.myflixAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
// creates setter and setter
@Data
// creates constructors
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private Date birthday;
    @DocumentReference
    private List<Movie> favoriteMovies;

    public User(String username, String password, String email, Date birthday, List<Movie> favoriteMovies) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.favoriteMovies = favoriteMovies;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
