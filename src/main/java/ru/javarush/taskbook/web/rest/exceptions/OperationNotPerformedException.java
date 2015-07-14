package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Александр on 30.01.15.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Operation is not performed")
public class OperationNotPerformedException extends RuntimeException {
}
