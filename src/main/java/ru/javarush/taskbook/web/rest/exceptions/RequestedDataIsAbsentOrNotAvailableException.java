package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ilyakhromov on 29.01.15.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason= "Received NULL instead of requested data.")
public class RequestedDataIsAbsentOrNotAvailableException extends RuntimeException {
    //TODO delete empty constructor
    public RequestedDataIsAbsentOrNotAvailableException() {
    }

    public RequestedDataIsAbsentOrNotAvailableException(String URI) {
        super("Requested URI: " + URI);
    }
}
