package com.v2nhung.bank.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestValidationBeforeAuthenFilter implements Filter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String header = req.getHeader(AUTHORIZATION);
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
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (username.equalsIgnoreCase("test")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        chain.doFilter(request, response);
    }
}
