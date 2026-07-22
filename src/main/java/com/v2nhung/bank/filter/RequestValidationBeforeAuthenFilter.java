package com.v2nhung.bank.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestValidationBeforeAuthenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader(AUTHORIZATION);
        if (authorization != null && !authorization.startsWith(BASIC)) {
            return true;
        }
        if (!request.getServletPath().equalsIgnoreCase("/user")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null || header.trim().isEmpty()) {
            throw new BadRequestException("Authorization header missing");
        }

        header = header.trim();
        if (!header.contains(BASIC)) {
            throw new BadRequestException("Basic authentication required");
        }
        if (header.equalsIgnoreCase(BASIC)) {
            throw new BadRequestException("Basic authentication required");
        }

        // handle validate username
        try {
            // convert header to username and password
            String base64Token = header.substring(6).trim();
            byte[] byteToken = Base64.getDecoder().decode(base64Token);
            String token = new String(byteToken, StandardCharsets.UTF_8);
            String delimiter = ":";
            String[] tokens = token.split(delimiter);

            if (tokens.length != 2) {
                throw new BadRequestException("Basic authentication required");
            }

            String username = tokens[0];
            String password = tokens[1];

            if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (username.equalsIgnoreCase("test")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        filterChain.doFilter(request, response);
    }
}
