package ru.javarush.taskbook.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javarush.taskbook.web.rest.exceptions.UnknownResourceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ilyakhromov on 20.02.15.
 */
@RestController
public class DefaultController {
    @RequestMapping("/rest/**")
    public void unmappedRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        throw new UnknownResourceException(uri);
    }
}