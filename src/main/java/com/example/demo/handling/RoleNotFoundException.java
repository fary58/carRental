package com.example.demo.handling;


public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleName) {
        super("Role not found with name: " + roleName);
    }

    // Additional constructor with a cause
    public RoleNotFoundException(String roleName, Throwable cause) {
        super("Role not found with name: " + roleName, cause);
    }

    // Additional methods, if needed
    public String getRoleName() {
        // Extract and return the role name
        // You can customize this method based on your requirements
        return getMessage().replace("Role not found with name: ", "");
    }
}


