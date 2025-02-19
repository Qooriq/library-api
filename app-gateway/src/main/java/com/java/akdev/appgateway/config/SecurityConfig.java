package com.java.akdev.appgateway.config;

import com.java.akdev.appgateway.login.LoginRoute;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebFilter;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

    public static final Authentication ANONYMOUS = new AnonymousAuthenticationToken(
            "anonymous",
            "anonymous",
            Collections.singleton(new SimpleGrantedAuthority("anonymous"))
    );

    @Value("${application.security.anonymous-access}")
    private String[] anonymousAccess;

    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    private final LoginRoute loginRoute;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .headers(ServerHttpSecurity.HeaderSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchangeSpec -> {
                    exchangeSpec.pathMatchers(anonymousAccess).permitAll();
                })
                .authorizeExchange(exchangeSpec -> exchangeSpec.anyExchange().authenticated())
                .oauth2ResourceServer(resourceServerSpec -> resourceServerSpec
                        .opaqueToken(spec -> {
                            var config = oAuth2ResourceServerProperties.getOpaquetoken();
                            spec.introspector(new NimbusReactiveOpaqueTokenIntrospector(config.getIntrospectionUri(), config.getClientId(), config.getClientSecret()));
                        })
                )
                .cors(corsSpec -> corsSpec.configurationSource(exchange -> new CorsConfiguration().applyPermitDefaultValues()))
                .addFilterAfter(webFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
                .build();
    }

    @NotNull
    private WebFilter webFilter() {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .defaultIfEmpty(ANONYMOUS)
                .flatMap(authentication -> {
                    if (authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
                        var writeableHeaders = new HttpHeaders();
                        writeableHeaders.putAll(exchange.getRequest().getHeaders());
                        var writeableRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                return writeableHeaders;
                            }
                        };
                        writeableHeaders.setBearerAuth(bearerTokenAuthentication.getToken().getTokenValue());
                        return chain.filter(exchange.mutate().request(writeableRequest).build());
                    }
                    return chain.filter(exchange);
                });

    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {
        return loginRoute.create(builder);
    }

    @Bean
    public NimbusReactiveOpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2ResourceServerProperties properties,
                                                                         WebClient webClient) {
        OAuth2ResourceServerProperties.Opaquetoken opaqueToken = properties.getOpaquetoken();
        return new NimbusReactiveOpaqueTokenIntrospector(opaqueToken.getIntrospectionUri(), webClient);
    }
}
