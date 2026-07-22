package com.v2nhung.bank.properties;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    public static String JWT_SECRET;
    public static String JWT_DEFAULT_VALUE;
    public static long JWT_EXPIRATION;

    @Value("${jwt.secret.value}")
    private String secretKey;

    @Value("${jwt.secret.defaultValue}")
    private String defaultValue;

    @Value("${jwt.secret.expiration}")
    private long expiration;

    @PostConstruct
    public void init() {
        JWT_SECRET = this.secretKey;
        JWT_EXPIRATION = this.expiration;
    }

    public static String getSecret() {
        if (JWT_SECRET == null) {
            return JWT_DEFAULT_VALUE;
        }
        return JWT_SECRET;
    }
}
