package com.hantaray.myflixAPI;

import lombok.Data;

@Data
public class AuthResponse {
    private String username;
    private String accessToken;

    public AuthResponse() { }

    public AuthResponse(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
}