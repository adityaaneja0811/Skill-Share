package com.org.skillshare.service;

import com.org.skillshare.dto.*;
import com.org.skillshare.entity.User;
import com.org.skillshare.repository.UserRepository;
import com.org.skillshare.util.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwt;
    private final long refreshExpiry;



    public AuthService(UserRepository repo, PasswordEncoder encoder,
                       AuthenticationManager authManager, JwtUtil jwt,
                       @org.springframework.beans.factory.annotation.Value("${app.jwt.refreshExpiry}") long refreshExpiry) {
        this.repo = repo; this.encoder = encoder; this.authManager = authManager; this.jwt = jwt; this.refreshExpiry = refreshExpiry;
    }

    public void register(SignupRequest req){
        if (repo.existsByEmail(req.getEmail())) throw new IllegalArgumentException("Email already registered");
        var user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        repo.save(user);
    }

    public JwtResponse login(LoginRequest req){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        String access = jwt.generate(req.getEmail(), Map.of("scope","ROLE_USER"));
        // simple refresh token (separate util with longer expiry or reuse with different expiry)
        String refresh = new com.org.skillshare.util.JwtUtil(
                ((java.util.Base64.getEncoder()).encodeToString("another-secret-change".getBytes())), refreshExpiry
        ).generate(req.getEmail(), Map.of("type","refresh"));
        return new JwtResponse(access, refresh);
    }
}
