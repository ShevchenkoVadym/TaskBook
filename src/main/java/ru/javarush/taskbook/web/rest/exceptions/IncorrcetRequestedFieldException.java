package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ilyakhromov on 12.02.15.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "field query parameters must be english letters only")
public class IncorrcetRequestedFieldException extends RuntimeException {
    public IncorrcetRequestedFieldException(String s){
        super("requested field = "+s+" must be represented by letters only");
    }
}
