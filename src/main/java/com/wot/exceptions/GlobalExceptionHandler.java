package com.wot.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String AccessDeniedException(Exception exception) {
        return "error_page";
    }

    @ExceptionHandler(Exception.class)
    public String GlobalExceptionHandler() {
        return "error_page";
    }
}
