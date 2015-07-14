package ru.javarush.taskbook.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User: blacky
 * Date: 25.12.14
 */
public class SameUrlAuthenticationSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
                                                 implements AuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SameUrlAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                throws IOException, ServletException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);

    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response);

        if (response.isCommitted())
            LOG.debug("Response has already been committed. Unable to redirect to " + targetUrl);

        // No redirect any more

    }

    /**
     * Removes temporary authentication-related data which may have been stored in the session
     * during the authentication process.
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
