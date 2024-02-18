package com.atipera.githubRepositoryViewerApp.controllers;

import com.atipera.githubRepositoryViewerApp.playload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.io.IOException;
import java.net.URISyntaxException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "";

        if (exception instanceof IOException) {
            status = HttpStatus.NOT_FOUND;
            message = "User not found";
        } else if (exception instanceof URISyntaxException) {
            status = HttpStatus.I_AM_A_TEAPOT;
            message = "Error during construction URL";
        } else {
            message = "There was an error processing your request";
        }
        return new ResponseEntity<>(new MessageResponse(message), status);
    }
}
