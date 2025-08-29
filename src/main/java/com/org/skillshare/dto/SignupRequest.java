package com.org.skillshare.dto;

import jakarta.validation.constraints.*;

public class SignupRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @Size(min=8) @NotBlank private String password;
    // getters/setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
