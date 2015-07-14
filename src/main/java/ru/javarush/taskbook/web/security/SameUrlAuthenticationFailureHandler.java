package ru.javarush.taskbook.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: blacky
 * Date: 25.12.14
 */
public class SameUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SameUrlLogoutSuccessHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
                throws IOException, ServletException {

        LOG.debug("No failure URL set, sending 401 Unauthorized error");

        // Don't redirect any more
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());

    }
}
