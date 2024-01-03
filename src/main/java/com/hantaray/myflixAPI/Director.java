package com.hantaray.myflixAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    private String name;
    private String bio;
    private int yearBirth;
    private Integer yearDeath; // Use Integer to allow null for yearDeath
}