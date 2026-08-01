package com.v2nhung.bank.filter;

import ch.qos.logback.core.util.StringUtil;
import com.v2nhung.bank.constant.BankConstant;
import com.v2nhung.bank.properties.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // get secret key from env
            String secret = JwtConfig.getSecret();
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            // build jwt
            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
            String jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", authorities)
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(secretKey)
                    .compact();

            // set jwt string to header
            response.setHeader(BankConstant.JWT_HEADER, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String oauth2Token = request.getHeader("Authorization");
        if (!StringUtil.isNullOrEmpty(oauth2Token) && !oauth2Token.startsWith("Basic ")) {
            return true;
        }

        // true -> ignore this filter
        // false -> run this filter when request coming
        return !request.getServletPath().equalsIgnoreCase("/user");
    }
}
