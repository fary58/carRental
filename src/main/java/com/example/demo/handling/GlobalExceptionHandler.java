package com.example.demo.handling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;


import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Log the exception for debugging purposes
        logger.severe("Error: " + ex.getMessage());
        ex.printStackTrace();

        // Handle other exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<String> handleUsernameExistsException(UsernameExistsException ex) {
        // Log the exception for debugging purposes
        logger.severe("UsernameExistsException: " + ex.getMessage());
        ex.printStackTrace();

        // Handle UsernameExistsException
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException ex) {
        // Log the exception for debugging purposes
        logger.severe("RoleNotFoundException: " + ex.getMessage());
        ex.printStackTrace();

        // Handle RoleNotFoundException
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
    }
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<String> handleRoleAlreadyExistsException(RoleAlreadyExistsException ex) {
        // Log the exception for debugging purposes
        logger.severe("RoleAlreadyExistsException: " + ex.getMessage());
        ex.printStackTrace();

        // Handle RoleAlreadyExistsException
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    // Add other exception handlers as needed
}


