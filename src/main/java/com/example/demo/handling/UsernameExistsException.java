package com.example.demo.handling;


public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException(String username) {
        super("User already exists with username: " + username);
    }

    // Additional constructor with a cause
    public UsernameExistsException(String username, Throwable cause) {
        super("User already exists with username: " + username, cause);
    }

    // Additional methods, if needed
    public String getUsername() {
        // Extract and return the username
        // You can customize this method based on your requirements
        return getMessage().replace("User already exists with username: ", "");
    }
}
