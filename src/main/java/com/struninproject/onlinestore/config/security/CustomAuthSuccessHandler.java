package com.struninproject.onlinestore.config.security;

import com.struninproject.onlinestore.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The {@code CustomAuthSuccessHandler } class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//    protected Log logger = LogFactory.getLog(this.getClass());

//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException {
//        handle(request, response, authentication);
//        clearAuthenticationAttributes(request);
//    }
//
//    protected void handle(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication
//    ) throws IOException {
//        String targetUrl = determineTargetUrl(authentication);
//        if (response.isCommitted()) {
//            logger.debug(
//                    "Response has already been committed. Unable to redirect to "
//                    + targetUrl);
//            return;
//        }
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//    }
//
//
//    protected String determineTargetUrl(final Authentication authentication) {
//
//        Map<String, String> roleTargetUrlMap = new HashMap<>();
//        roleTargetUrlMap.put("USER", "/");
//        roleTargetUrlMap.put("ADMIN", "/user");
//
//        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (final GrantedAuthority grantedAuthority : authorities) {
//            String authorityName = grantedAuthority.getAuthority();
//            if(roleTargetUrlMap.containsKey(authorityName)) {
//                return roleTargetUrlMap.get(authorityName);
//            }
//        }
//
//        throw new IllegalStateException();
//    }

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
//        response.setStatus(HttpServletResponse.SC_OK);

        if (authentication.isAuthenticated()) {
            final String refererUrl = request.getHeader("Referer");
            if (refererUrl.matches(".*/login.*")) {
                response.sendRedirect("/");
            } else {
                response.sendRedirect(refererUrl);
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
