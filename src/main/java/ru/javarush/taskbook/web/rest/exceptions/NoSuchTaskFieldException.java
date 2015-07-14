package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ilyakhromov on 12.02.15.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "requested field is not represented in the Task.class ")
public class NoSuchTaskFieldException extends RuntimeException {
    public NoSuchTaskFieldException(String field){
        super("field \""+field+"\" is not represented in the Task.class");
    }
}
