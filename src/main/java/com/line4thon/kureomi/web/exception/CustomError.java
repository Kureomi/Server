package com.line4thon.kureomi.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomError extends RuntimeException{
    private final HttpStatus httpStatus;

    public CustomError(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
