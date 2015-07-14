package ru.javarush.taskbook.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: blacky
 * Date: 25.12.14
 */
public class SameUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SameUrlLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
                throws IOException, ServletException {

        LOG.debug(request.getHeader("Referer"));

        // Don't redirect any more
        // http://stackoverflow.com/questions/11454729/spring-security-3-1-redirect-after-logout
        //response.sendRedirect(refererUrl);

    }
}
