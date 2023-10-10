package com.dms.gatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RefreshScope
@Component
@Slf4j
public class AuthenticationFilterFactory extends AbstractGatewayFilterFactory<AuthenticationFilterFactory.Authentication> {

    @Autowired
    private RouterValidator routerValidator; //custom route validator

    @Autowired
    private JwtService jwtService;

    public AuthenticationFilterFactory() {
        super(Authentication.class);
    }

    private final static String BEARER = "Bearer";

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(errorMessage);
        return response.setComplete();
    }

    private List<String> getAuthorizationHeaders(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
    }

    private boolean isAuthorizationMissing(ServerHttpRequest request) {
        return CollectionUtils.isEmpty(getAuthorizationHeaders(request));
    }

    private boolean validToken(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String token = getAuthorizationHeaders(request).get(0);

        if (StringUtils.isNotBlank(token) && StringUtils.startsWith(token, BEARER)) {
            token = StringUtils.remove(token, BEARER).trim();
        } else {
            return false;
        }

        return StringUtils.isNotBlank(token) && jwtService.isTokenValid(token);
    }

    @Override
    public GatewayFilter apply(Authentication authentication) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (routerValidator.isSecured.test(request)) {
                if (this.isAuthorizationMissing(request)) {
                    return this.onError(exchange, "Authorization in RequestHeader is missing", HttpStatus.UNAUTHORIZED);
                }

                if (!validToken(exchange)) {
                    return this.onError(exchange, "Invalid token in Authorization", HttpStatus.UNAUTHORIZED);
                }

//                this.populateRequestWithHeaders(request, token);
            }

            return chain.filter(exchange);
//                    .then(Mono.fromRunnable(() -> {
//                        var response = exchange.getResponse();
//                        response.getHeaders().add();
//                        response.setRawStatusCode(201);
//                        exchange.mutate().response(response).build();
//                    }));
        };
    }

    // TODO: Create a service function to set this in register and login in auth-service
    private void populateRequestWithHeaders(ServerHttpRequest request, String token) {
        request.mutate()
                .header("userId", String.valueOf(jwtService.extractClaimByKey(token, "userId")))
                .header("username", String.valueOf(jwtService.extractClaimByKey(token, "username")))
                .header("roleId", String.valueOf(jwtService.extractClaimByKey(token, "roleId")))
                .header("roleName", String.valueOf(jwtService.extractClaimByKey(token, "roleName")))
                .build();
    }

    public static class Authentication {
    }
}
