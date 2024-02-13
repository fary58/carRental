package com.example.demo.JunitTest;

import com.example.demo.Controller.UserController;

import com.example.demo.Entity.User;

import com.example.demo.Service.DispatchService;
import com.example.demo.Service.RoleService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private DispatchService dispatchService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateUser_UsernameExistsException() {
        User user = new User();
        user.setUsername("existingUser");

        when(userService.usernameExists("existingUser")).thenReturn(true);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) userController.createUser(user, mockBindingResult());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof String);
        assertEquals("User already exists with username: existingUser", responseEntity.getBody());
    }



    // Similar tests can be added for other methods in UserController

    private BindingResult mockBindingResult() {
        return mock(BindingResult.class);
    }
}
