package com.example.demo.JunitTest;

import com.example.demo.Controller.RoleController;
import com.example.demo.Entity.Role;
import com.example.demo.Respository.RoleRepository;
import com.example.demo.Service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleRepository roleRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testGetRoleById_Success() {
        // Mocking
        Role role = new Role();
        role.setRole_id(1L);

        when(roleService.getRoleById(1L)).thenReturn(role);

        // Test
        ResponseEntity<Role> responseEntity = roleController.getRoleById(1L);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Role.class, responseEntity.getBody().getClass());
        assertEquals(1L, responseEntity.getBody().getRole_id());

        // Verify interactions
        verify(roleService, times(1)).getRoleById(1L);
    }



    @Test
    void testGetAllRoles_Success() {
        // Mocking
        Role role = new Role();
        role.setRole_id(1L);

        when(roleService.getAllRoles()).thenReturn(Collections.singletonList(role));

        // Test
        ResponseEntity<List<Role>> responseEntity = roleController.getAllRoles();

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Collections.singletonList(role).getClass(), responseEntity.getBody().getClass());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getRole_id());

        // Verify interactions
        verify(roleService, times(1)).getAllRoles();
    }

    private BindingResult mockBindingResult() {
        return mock(BindingResult.class);
    }
}
