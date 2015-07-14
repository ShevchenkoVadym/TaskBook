package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ilyakhromov on 11.02.15.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Sorting parameters provided could not be found")
public class SortingOptionIsInvalidException extends RuntimeException {
    public SortingOptionIsInvalidException(String s){
        super(s);
    }
}
