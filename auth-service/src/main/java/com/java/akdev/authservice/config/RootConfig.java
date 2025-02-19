package com.java.akdev.authservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RootConfig {
    @Value("${application.keycloak.host}")
    private String keycloakHost;
    @Value("${application.keycloak.realm.admin}")
    private String keycloakAdminRealm;
    @Value("${application.keycloak.username}")
    private String keycloakAdminUsername;
    @Value("${application.keycloak.password}")
    private String keycloakAdminPassword;
    @Value("${application.keycloak.clientId}")
    private String keycloakAdminClientId;
    @Value("${application.keycloak.clientSecret}")
    private String keycloakAdminClientSecret;

    @Bean
    public Keycloak keycloak() {
        return Keycloak.getInstance(
                keycloakHost,
                keycloakAdminRealm,
                keycloakAdminUsername,
                keycloakAdminPassword,
                keycloakAdminClientId,
                keycloakAdminClientSecret
        );
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
