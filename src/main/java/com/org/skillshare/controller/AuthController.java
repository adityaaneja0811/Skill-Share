package com.org.skillshare.controller;

import com.org.skillshare.dto.*;
import com.org.skillshare.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.org.skillshare.util.JwtUtil;
import com.org.skillshare.config.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService=authService;
    }

    //public AuthController(AuthService authService){ this.authService=authService; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest req){
        authService.register(req);
        System.out.println("In register controller");
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest req){
//        return ResponseEntity.ok(authService.login(req));
//    }
@PostMapping("/login")
public Map<String, Object> login(@RequestParam String email, @RequestParam String password) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
    );

    String token = jwtUtil.generate(email, Map.of());
    boolean valid = jwtUtil.validate(token, email);

    return Map.of(
            "token", token,
            "profile", valid ? "👤 Profile page for user: " + email : "Invalid token!"
    );
}

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        // Stateless JWT: client discards token. For blacklisting, store token/jti in DB/Redis with expiry.
        return ResponseEntity.ok().build();
    }
}
