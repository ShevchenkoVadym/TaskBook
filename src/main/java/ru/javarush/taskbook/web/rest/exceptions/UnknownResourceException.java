package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URL;

/**
 * Created by ilyakhromov on 20.02.15.
 */

public class UnknownResourceException extends RuntimeException {
    public UnknownResourceException(String uri){
        super("There is no resource for path " + uri);
    }
}
