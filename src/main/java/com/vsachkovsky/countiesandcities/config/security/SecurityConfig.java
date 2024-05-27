package com.vsachkovsky.countiesandcities.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {

    private static final String[] EDITOR_RIGHTS = {
            "/api/countries-application/countries/update",
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(authorize -> authorize
                        .pathMatchers(EDITOR_RIGHTS).hasAnyRole("EDITOR")
                        .anyExchange().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(createJWTConverter())
                        ))
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    private ReactiveJwtAuthenticationConverterAdapter createJWTConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new RoleConverter());

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


}
