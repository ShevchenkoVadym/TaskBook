package ru.javarush.taskbook.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Task")  //404
public class NoSuchTaskException extends RuntimeException {
    public NoSuchTaskException(){}
    public NoSuchTaskException(String id){
        super("Task with id: " + id + " is not found on the Database");
    }

}
