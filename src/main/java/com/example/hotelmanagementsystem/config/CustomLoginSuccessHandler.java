package com.example.hotelmanagementsystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String redirectUrl = request.getContextPath();

        String userRole = userDetails.getAuthorities().toString();
        if(userRole.equals("[ROLE_ADMIN]")){
            redirectUrl = "/admin/";
        } else if (userRole.equals("[ROLE_USER]")) {
            redirectUrl = "/";
        } else if (userRole.equals("[ROLE_RECEPTION]")) {
            redirectUrl = "/reception/";
        }else{
            redirectUrl="/manager/";
        }

        response.sendRedirect(redirectUrl);
    }
}
