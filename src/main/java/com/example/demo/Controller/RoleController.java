package com.example.demo.Controller;


import com.example.demo.Entity.Role;
import com.example.demo.Respository.RoleRepository;
import com.example.demo.Service.RoleService;
import com.example.demo.handling.RoleAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;
    private static final Logger logger = Logger.getLogger(RoleController.class.getName());




    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create_role")
    public Object createRole(@Valid @RequestBody Role roleRequest, BindingResult bindingResult) {
        try {

            Optional<Role> existingRoleOptional = roleRepository.findByName(roleRequest.getName());
            if (existingRoleOptional.isPresent()) {
                // Role with the same name already exists, throw an exception
                throw new RoleAlreadyExistsException(roleRequest.getName());
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

            // Save the role and associated users
            Role savedRole = roleService.saveRoleWithUsers(roleRequest);

            return savedRole;

        } catch (Exception e) {
            // Handle other exceptions
            logger.severe("Error creating role with users: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating role");
        }
    }


    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@Valid @PathVariable Long roleId) {
        Role role = roleService.getRoleById(roleId);
        if (role != null) {
            return new ResponseEntity<>(role, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}

