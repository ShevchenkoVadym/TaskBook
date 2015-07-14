package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Missing important data")
public class MissingDataException extends RuntimeException {
}
