package com.v2nhung.bank.filter;

import com.v2nhung.bank.constant.BankConstant;
import com.v2nhung.bank.properties.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(BankConstant.JWT_HEADER);

        if (jwt != null) {
            String secret = JwtConfig.getSecret();
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parser().verifyWith(secretKey)
                    .build().parseSignedClaims(jwt).getPayload();
            String username = claims.get("username", String.class);
            String authorities = claims.get("authorities", String.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // do next filter
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String header = request.getHeader(BankConstant.JWT_HEADER);
        if (!StringUtils.isBlank(header) && !header.startsWith("Basic ")) {
            return false;
        }

        // true -> ignore this filter
        // false -> run this filter when request coming
        // -> not match with /user -> run this filter -> validate jwt token
        List<String> requestNotValidates = Arrays.asList("/user", "/login", "/contact", "/oauth2");
        for (String notValidate : requestNotValidates) {
            return request.getServletPath().equalsIgnoreCase(notValidate);
        }
        return false;
    }
}
