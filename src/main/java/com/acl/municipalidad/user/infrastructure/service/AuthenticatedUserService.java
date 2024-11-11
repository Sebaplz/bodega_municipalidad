package com.acl.municipalidad.user.infrastructure.service;

import com.acl.municipalidad.user.domain.exceptions.EmailNotFoundException;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.service.IUserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserService {

    private final IUserQueryService IUserQueryService;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new RuntimeException("User is not authenticated");
        }

        String email = userDetails.getUsername();

        return IUserQueryService.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("User not found"));
    }
}
