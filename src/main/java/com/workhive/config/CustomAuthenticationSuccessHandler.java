package com.workhive.config;

import com.workhive.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Role role = userDetails.getUser().getRole();

        if (role == Role.ROLE_RECRUITER) {
            response.sendRedirect(request.getContextPath() + "/recruiter/dashboard");
        } else if (role == Role.ROLE_JOB_SEEKER) {
            response.sendRedirect(request.getContextPath() + "/seeker/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
