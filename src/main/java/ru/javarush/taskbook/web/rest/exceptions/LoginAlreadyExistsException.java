package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: blacky
 * Date: 10.01.15
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Login already exists")
public class LoginAlreadyExistsException extends RuntimeException {
}
