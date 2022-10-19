package com.struninproject.onlinestore.config.security;

import com.struninproject.onlinestore.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The {@code AuthSuccessHandler } class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("user", authUser);
        session.setAttribute("username", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());


        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);


        if (authentication.isAuthenticated()) {
            final String refererUrl = request.getHeader("Referer");
            System.out.println(refererUrl);
            if (refererUrl.endsWith("/login") || // FIXME: 19.10.2022 need pattern
                refererUrl.endsWith("/login?error=true")) {
                response.sendRedirect("/");
            } else {
                response.sendRedirect(refererUrl);
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
