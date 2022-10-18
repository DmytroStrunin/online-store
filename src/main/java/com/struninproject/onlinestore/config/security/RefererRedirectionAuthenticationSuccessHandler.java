package com.struninproject.onlinestore.config.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * The {@code RefererRedirectionAuthenticationSuccessHandler} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
class RefererRedirectionAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    public RefererRedirectionAuthenticationSuccessHandler() {
        setUseReferer(true);
    }
}