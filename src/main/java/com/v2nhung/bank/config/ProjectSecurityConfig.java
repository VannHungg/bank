package com.v2nhung.bank.config;

import com.v2nhung.bank.filter.AuthoritiesLogginAfterFilter;
import com.v2nhung.bank.filter.CsrfTokenFilter;
import com.v2nhung.bank.filter.JWTTokenGeneratorFilter;
import com.v2nhung.bank.filter.JWTTokenValidatorFilter;
import com.v2nhung.bank.filter.RequestValidationBeforeAuthenFilter;
import com.v2nhung.bank.handler.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // config session
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // config cors
                .cors(corsConfig -> corsConfig.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(Collections.singletonList("Authorization"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                }))

                // config csrf
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers("/contact", "/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .addFilterBefore(new RequestValidationBeforeAuthenFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new CsrfTokenFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLogginAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())

                // authorization
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 ->
                        oauth2.successHandler(oAuth2AuthenticationSuccessHandler));

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/favicon.ico",
                "/.well-known/**",
                "/error"
        );
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration github = githubClientRegistration();
        return new InMemoryClientRegistrationRepository(github);
    }

    private ClientRegistration githubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("Ov23ligggCnYyP3ifSx3")
                .clientSecret("c47b3d8efc1baca7665e888adc3ba6447bd241ca")
                .scope("read:user", "user:email")
                .build();
    }
}
