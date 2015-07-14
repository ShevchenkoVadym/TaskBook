package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ilyakhromov on 12.02.15.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = " \"author\" field must be english letters only or digits")
public class IncorrcetRequestedNameException extends RuntimeException{
    public IncorrcetRequestedNameException(String name){
        super("\"author\" = "+name+" must be english letters only or digits");
    }
}
