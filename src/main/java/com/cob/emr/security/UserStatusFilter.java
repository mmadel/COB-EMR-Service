package com.cob.emr.security;

import com.cob.emr.entity.security.user.ClericalEMRUser;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.enums.UserStatus;
import com.cob.emr.repositories.security.ClericalEMRUserRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserStatusFilter extends OncePerRequestFilter {

    @Autowired
    private ClinicalEMRUserRepository clinicalEMRUserRepository;

    @Autowired
    private ClericalEMRUserRepository clericalEMRUserRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        String uuid = jwt.getSubject();

        if (uuid == null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserStatus status = findUserStatus(uuid);
        if (status == UserStatus.INACTIVE) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new HashMap<>();
            body.put("message", "User account is inactive");
            body.put("status", HttpStatus.FORBIDDEN.value());
            body.put("time-stamp", new Date().getTime());

            objectMapper.writeValue(response.getOutputStream(), body);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private UserStatus findUserStatus(String uuid) {
        Optional<ClinicalEMRUser> clinicalUser = clinicalEMRUserRepository.findByUUID(uuid);
        if (clinicalUser.isPresent()) {
            return clinicalUser.get().getStatus();
        }

        Optional<ClericalEMRUser> clericalUser = clericalEMRUserRepository.findByUUID(uuid);
        if (clericalUser.isPresent()) {
            return clericalUser.get().getStatus();
        }

        return null;
    }
}
