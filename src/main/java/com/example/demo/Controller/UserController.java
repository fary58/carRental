package com.example.demo.Controller;

import com.example.demo.Entity.Dispatch;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;

import com.example.demo.Entity.Vehicle;
import com.example.demo.Service.DispatchService;
import com.example.demo.Service.RoleService;
import com.example.demo.Service.VehicleService;
import com.example.demo.handling.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import com.example.demo.Service.UserService;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DispatchService dispatchService;


    private static final Logger logger = Logger.getLogger(UserController.class.getName());


    @PostMapping("/createUserWithRole")
    public Object createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        try {
            // Check if the username already exists
            if (userService.usernameExists(user.getUsername())) {
                throw new UsernameExistsException(user.getUsername());
            }

            if (bindingResult.hasErrors()) {
                // Create a map to store field-level errors
                Map<String, String> fieldErrors = new HashMap<>();

                // Log validation errors and populate the map
                for (FieldError error : bindingResult.getFieldErrors()) {
                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();
                    fieldErrors.put(fieldName, errorMessage);
                    logger.severe(fieldName + ": " + errorMessage);
                }

                // Return a bad request response with field-level errors
                return ResponseEntity.badRequest().body(fieldErrors);
            }

            Role specifiedRole = null;

            if (user.getRole() == null || user.getRole().getName() == null) {
                com.example.demo.Entity.Role defaultRole = roleService.findByName("USER");
                if (defaultRole == null) {
                    throw new RoleNotFoundException("USER");
                }
                specifiedRole = defaultRole;
            } else {
                specifiedRole = roleService.findByName(user.getRole().getName());
                if (specifiedRole == null) {
                    // If the role does not exist, create a new one with the specified name
                    specifiedRole = new Role();
                    specifiedRole.setName(user.getRole().getName());
                    specifiedRole = roleService.saveRole(specifiedRole, null);
                }
            }

            // Set the specified role in the user
            user.setRole(specifiedRole);

            // Log the user details before saving
            logger.info("User details before saving: " + user);

            // Save the user
            User savedUser = userService.saveUser(user);

            savedUser = userService.saveUser(savedUser);
            return savedUser;


        } catch (UsernameExistsException e) {
        // Handle username exists exception
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.severe("Error creating user: " + e.getMessage());
            e.printStackTrace();

            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }

    }

    // Retrieve user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Delete user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Update user
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/createUserWithVehicles")
    public Object createUserWithVehicles(@Valid @RequestBody User user, BindingResult bindingResult) {
        try {
            // Check if the username already exists
            if (bindingResult.hasErrors()) {
                // Create a map to store field-level errors
                Map<String, String> fieldErrors = new HashMap<>();

                // Log validation errors and populate the map
                for (FieldError error : bindingResult.getFieldErrors()) {
                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();
                    fieldErrors.put(fieldName, errorMessage);
                    logger.severe(fieldName + ": " + errorMessage);
                }

                // Return a bad request response with field-level errors
                return ResponseEntity.badRequest().body(fieldErrors);
            }

            Role specifiedRole = null;

            if (user.getRole() == null || user.getRole().getName() == null) {
                com.example.demo.Entity.Role defaultRole = roleService.findByName("USER");
                if (defaultRole == null) {
                    throw new RoleNotFoundException("USER");
                }
                specifiedRole = defaultRole;
            } else {
                specifiedRole = roleService.findByName(user.getRole().getName());
                if (specifiedRole == null) {
                    // If the role does not exist, create a new one with the specified name
                    specifiedRole = new Role();
                    specifiedRole.setName(user.getRole().getName());
                    specifiedRole = roleService.saveRole(specifiedRole, null);
                }
            }

            // Set the role in the user
            user.setRole(specifiedRole);

            // Save the user (without vehicles) to get the user ID
            User savedUser = userService.saveUser(user);

            // Associate vehicles with the saved user
            if (user.getVehicles() != null) {
                for (Vehicle vehicle : user.getVehicles()) {
                    vehicle.setUser(savedUser);
                    vehicleService.saveVehicle(vehicle);
                }
            }

            // Set the saved user back to the response
            savedUser.setVehicles(user.getVehicles());

            return savedUser;
        } catch (UsernameExistsException e) {
            // Handle username exists exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            logger.severe("Error creating user with vehicles: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

    // Helper method to check if the user has the "admin" role
//    private boolean hasAdminRole(UserDetails userDetails) {
//        return userDetails.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
//    }


    @PostMapping("/createUserWithVehiclesAndDispatches")
    public Object createUserWithVehiclesAndDispatches(@Valid @RequestBody User userRequest,  BindingResult bindingResult) {
        try {
            // Check if the username already exists
            if (userService.usernameExists(userRequest.getUsername())) {
                throw new UsernameExistsException(userRequest.getUsername());
            }
            if (bindingResult.hasErrors()) {
                // Create a map to store field-level errors
                Map<String, String> fieldErrors = new HashMap<>();

                // Log validation errors and populate the map
                for (FieldError error : bindingResult.getFieldErrors()) {
                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();
                    fieldErrors.put(fieldName, errorMessage);
                    logger.severe(fieldName + ": " + errorMessage);
                }

                // Return a bad request response with field-level errors
                return ResponseEntity.badRequest().body(fieldErrors);
            }

            Role specifiedRole = null;

            if (userRequest.getRole() == null || userRequest.getRole().getName() == null) {
                com.example.demo.Entity.Role defaultRole = roleService.findByName("USER");
                if (defaultRole == null) {
                    throw new RoleNotFoundException("USER");
                }
                specifiedRole = defaultRole;
            } else {
                specifiedRole = roleService.findByName(userRequest.getRole().getName());
                if (specifiedRole == null) {
                    // If the role does not exist, create a new one with the specified name
                    specifiedRole = new Role();
                    specifiedRole.setName(userRequest.getRole().getName());
                    specifiedRole = roleService.saveRole(specifiedRole, null);
                }
            }

            // Set the role in the user
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setRole(specifiedRole);

            // Save the user (without vehicles and dispatches) to get the user ID
            User savedUser = userService.saveUser(user);

            // Associate vehicles with the saved user
            if (userRequest.getVehicles() != null) {
                for (Vehicle vehicle : userRequest.getVehicles()) {
                    vehicle.setUser(savedUser);
                    vehicleService.saveVehicle(vehicle);
                }
            }

            // Associate dispatches with the saved user
            if (userRequest.getDispatches() != null) {
                for (Dispatch dispatch : userRequest.getDispatches()) {
                    dispatch.setUser(savedUser);
                    dispatchService.saveDispatch(dispatch);
                }
            }

            // Set the saved user back to the response
            savedUser.setVehicles(userRequest.getVehicles());
            savedUser.setDispatches(userRequest.getDispatches());

            return savedUser;
        } catch (UsernameExistsException e) {
            // Handle username exists exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            logger.severe("Error creating user with vehicles and dispatches: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

}















