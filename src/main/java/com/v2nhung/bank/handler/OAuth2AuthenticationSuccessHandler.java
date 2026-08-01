package com.v2nhung.bank.handler;

import com.v2nhung.bank.data.entity.AuthoritiesEntity;
import com.v2nhung.bank.data.entity.CustomerEntity;
import com.v2nhung.bank.data.repository.AuthoritiesRepository;
import com.v2nhung.bank.data.repository.CustomerRepository;
import com.v2nhung.bank.properties.JwtConfig;
import com.v2nhung.bank.util.enums.AuthProviderEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomerRepository customerRepository;
    private final AuthoritiesRepository authoritiesRepository;

    @Value("${app.oauth2.redirect-uri:http://localhost:4200/login}")
    private String redirectUri;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String login = oAuth2User.getAttribute("login");

        if (ObjectUtils.isEmpty(email)) {
            email = login + "@github.com";
        }
        if (ObjectUtils.isEmpty(name)) {
            name = login;
        }

        CustomerEntity customer = customerRepository.findByEmail(email).orElse(new CustomerEntity());
        if (!ObjectUtils.isEmpty(customer) && ObjectUtils.isEmpty(customer.getEmail())) {
            customer.setEmail(email);
            customer.setName(name);
            customer.setAuthProvider(AuthProviderEnum.GITHUB);
            customer.setPwd("");

            AuthoritiesEntity authorities = AuthoritiesEntity.builder()
                    .name("ROLE_USER").build();
            customer.setAuthorities(authorities);
            customer = customerRepository.save(customer);
        }

        String secret = JwtConfig.getSecret();
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // build jwt
        String authorities = customer.getAuthorities().stream()
                .map(AuthoritiesEntity::getName).collect(Collectors.joining(","));
        String jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
                .claim("username", email)
                .claim("authorities", authorities)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 30000000))
                .signWith(secretKey)
                .compact();

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                        .queryParam("token", jwt).build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
