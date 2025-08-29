package com.org.skillshare.dto;

public class JwtResponse {
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    public JwtResponse(String token, String refreshToken){ this.token=token; this.refreshToken=refreshToken; }
    // getters

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
