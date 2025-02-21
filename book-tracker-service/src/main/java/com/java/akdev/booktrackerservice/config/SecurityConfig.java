package com.java.akdev.booktrackerservice.config;

import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry.anyRequest().authenticated())
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
                try {
                    var signedJwt = SignedJWT.parse(bearer.getToken());
                    var claimsSet = signedJwt.getJWTClaimsSet();

                    Map<String, Object> claims = new HashMap<>(claimsSet.getClaims());
                    claims.computeIfPresent("exp", (key, value) -> toInstant(value));
                    claims.computeIfPresent("iat", (key, value) -> toInstant(value));
                    claims.computeIfPresent("nbf", (key, value) -> toInstant(value));

                    Jwt jwt = Jwt.withTokenValue(bearer.getToken())
                            .claims((c) -> c.putAll(claims))
                            .headers((m) -> m.putAll(signedJwt.getHeader().toJSONObject()))
                            .build();

                    AbstractAuthenticationToken token = jwtAuthenticationConverter().convert(jwt);
                    if (token.getDetails() == null) {
                        token.setDetails(bearer.getDetails());
                    }
                    return token;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }


            @Override
            public boolean supports(Class<?> authentication) {
                return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
            }

            private Instant toInstant(Object value) {
                if (value instanceof Date) {
                    return ((Date) value).toInstant();
                }
                return (Instant) value;
            }

        };
    }

    @Bean
    public BearerTokenAuthenticationFilter authenticationFilter() {
        return new BearerTokenAuthenticationFilter(new ProviderManager(authProvider()));
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

}
