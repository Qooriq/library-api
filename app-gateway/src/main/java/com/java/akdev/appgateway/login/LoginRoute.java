package com.java.akdev.appgateway.login;

import com.java.akdev.appgateway.dto.AccessTokenResponse;
import com.java.akdev.appgateway.dto.LoginRequest;
import com.java.akdev.appgateway.enums.CookieName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class LoginRoute {

    private static final String GRANT_TYPE_PASSWORD = "password";
    public static final String LOGIN_PATH = "/api/v1/auth/login";

    @Value("${application.keycloak.clientId}")
    private String clientId;
    @Value("${application.keycloak.clientSecret}")
    private String clientSecret;
    @Value("${application.keycloak.realm}")
    private String realm;
    @Value("${application.keycloak.host}")
    private String keycloakHost;
    @Value("${application.keycloak.authScope}")
    private String authScope;

    public RouteLocator create(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("login", r -> r
                        .path(LOGIN_PATH)
                        .filters(f -> f
                                .rewritePath(
                                        LOGIN_PATH, "/realms/" + realm + "/protocol/openid-connect/token"
                                ).modifyRequestBody(
                                        LoginRequest.class, MultiValueMap.class,
                                        MediaType.APPLICATION_FORM_URLENCODED_VALUE, convertForLoginFunction()
                                ).modifyResponseBody(Map.class, AccessTokenResponse.class, modifyResponseFunction())
                        )
                        .uri(keycloakHost)
                )
                .build();
    }

    private RewriteFunction<LoginRequest, MultiValueMap> convertForLoginFunction() {
        return (exchange, loginRequest) -> {
            var res = new LinkedMultiValueMap<String, String>();
            res.put("username", List.of(loginRequest.username()));
            res.put("password", List.of(loginRequest.password()));
            res.put("scope", List.of(authScope));
            res.put("grant_type", List.of(GRANT_TYPE_PASSWORD));
            res.put("client_id", List.of(clientId));
            res.put("client_secret", List.of(clientSecret));
            return Mono.just(res);
        };
    }


    private RewriteFunction<Map, AccessTokenResponse> modifyResponseFunction() {
        return (exchange, map) -> {
            var res = new AccessTokenResponse();
            var response = exchange.getResponse();
            if (response.getStatusCode() != null && response.getStatusCode().is2xxSuccessful()) {
                res.setAccessToken((String) map.get(CookieName.ACCESS_TOKEN.name().toLowerCase()));
                res.setRefreshToken((String) map.get(CookieName.REFRESH_TOKEN.name().toLowerCase()));
                return Mono.just(res);
            }
            res.setMessage("Wrong login or password");
            res.setCode(400);
            return Mono.just(res);
        };
    }
}
