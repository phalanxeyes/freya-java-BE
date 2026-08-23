package com.example.freya.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handles Exceptions and gives http codes
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        return createProblem(HttpStatus.BAD_REQUEST, ex.getMessage()) ;
    }

    @ExceptionHandler(IDNotFoundException.class)
    public ProblemDetail handleUserNotFound(IDNotFoundException ex) {
        return createProblem(HttpStatus.NOT_FOUND, ex.getMessage()) ;
    }

    // problem detail with message property
    private ProblemDetail createProblem(HttpStatus status, String message) {
        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setProperty("message", message);
        return problem;
    }
}
