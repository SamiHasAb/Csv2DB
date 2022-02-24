package com.example.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongSearchCombinationException extends RuntimeException{
    public WrongSearchCombinationException(String message) {
        super(message);
    }
}
