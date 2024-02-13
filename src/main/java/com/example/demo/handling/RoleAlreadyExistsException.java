package com.example.demo.handling;

public class RoleAlreadyExistsException extends RuntimeException {

    public RoleAlreadyExistsException(String username) {
        super("Role Name   : " + username);
    }
}
