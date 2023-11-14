package com.cob.emr.security;


import com.cob.emr.service.security.SecurityFinderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    SecurityFinderService securityFinderService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter =
                new DelegatingJwtGrantedAuthoritiesConverter(
                        new JwtGrantedAuthoritiesConverter(),
                        new KeycloakJwtRolesConverter());
        httpSecurity.oauth2ResourceServer().jwt().jwtAuthenticationConverter(
                jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt)));
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        return httpSecurity.build();
    }
}
