package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ilyakhromov on 21.01.15.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect Id, must be hex only")
public class IncorrectTaskIdException extends RuntimeException{
    public IncorrectTaskIdException(String id){
        super("This id: " + id + " must be represented by hex numbers only");
    }
}
