package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No such User")  //400
public class NoSuchUserException extends RuntimeException {
    private String name;

    public NoSuchUserException(String name){
        super("No such user with name: \"" + name + "\".");
        this.name = name;
    }
}
