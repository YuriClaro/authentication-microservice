package com.humanit.authentication_microservice.model;

public class SignInRequest {
    private String username;
    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
