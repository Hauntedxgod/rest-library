package ru.maxima.restlibrary.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String name){
        return null;
    }
}
