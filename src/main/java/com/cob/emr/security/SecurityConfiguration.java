package com.cob.emr.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration {
    //    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//    @Autowired
//    @Qualifier("handlerRestAccessDenied")
//    RestAccessDeniedHandler restAccessDeniedHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter =
                new DelegatingJwtGrantedAuthoritiesConverter(
                        new JwtGrantedAuthoritiesConverter(),
                        new KeycloakJwtRolesConverter());

        httpSecurity
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/actuator/**").permitAll() // Allow actuator endpoints
                        .anyRequest().authenticated()            // Secure all other endpoints
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        jwtToken -> new JwtAuthenticationToken(jwtToken, authoritiesConverter.convert(jwtToken))
                                )
                        )
                );

        return httpSecurity.build();
    }
}
