package com.struninproject.onlinestore.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code RefererRedirectionLogoutSuccessHandler} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */

public class CustomLogoutSuccessHandler extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {


//        System.out.println(response.getStatus());

        //set our response to OK status
//        response.setStatus(HttpServletResponse.SC_OK);

//        final String refererUrl = request.getHeader("Referer");
//        response.sendRedirect(refererUrl);
        super.onLogoutSuccess(request, response, authentication);
    }
}
