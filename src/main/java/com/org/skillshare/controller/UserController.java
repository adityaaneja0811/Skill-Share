package com.org.skillshare.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public String me(org.springframework.security.core.Authentication auth){
        return "Hello, " + auth.getName();
    }

    @GetMapping("/profile")
    public String getProfile() {
        System.out.println("In the profile controller");
        return "👤 This is a secured profile endpoint. Token is valid!";
    }
}
