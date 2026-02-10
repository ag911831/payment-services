package com.example.payment_services.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIlligalException(IllegalArgumentException x){
        String response = " !  user is already persent";
        return response;
    }
    @ExceptionHandler(AuthenticationException.class)
    public String handleUserNotFoundExcepiton(AuthenticationException u){

        String response = "Invalid Username/Password ....";

        return response;

    }
}
