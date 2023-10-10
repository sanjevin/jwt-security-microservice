package com.dms.authservice.service;

public interface JwtService {

    String extractUserName(String token);

    String generateToken(String username);

    boolean isTokenValid(String token, String username);

}
