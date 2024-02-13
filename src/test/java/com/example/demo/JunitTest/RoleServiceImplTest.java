package com.example.demo.JunitTest;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Respository.RoleRepository;
import com.example.demo.Respository.UserRepository;
import com.example.demo.ServiceImpl.RoleServiceImpl;
import com.example.demo.handling.RoleAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRole() {
        Role role = new Role();
        role.setName("ROLE_USER");

        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role savedRole = roleService.saveRole(role, null);

        assertNotNull(savedRole);
        assertEquals("ROLE_USER", savedRole.getName());
    }

    @Test
    void testGetRoleById() {
        Role role = new Role();
        role.setRole_id(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role retrievedRole = roleService.getRoleById(1L);

        assertNotNull(retrievedRole);
        assertEquals(1L, retrievedRole.getRole_id());
    }

    @Test
    void testGetAllRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.add(new Role());

        when(roleRepository.findAll()).thenReturn(roleList);

        List<Role> retrievedRoles = roleService.getAllRoles();

        assertNotNull(retrievedRoles);
        assertEquals(roleList.size(), retrievedRoles.size());
    }

    @Test
    void testFindByName() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));

        Role retrievedRole = roleService.findByName("ROLE_ADMIN");

        assertNotNull(retrievedRole);
        assertEquals("ROLE_ADMIN", retrievedRole.getName());
    }

    @Test
    void testSaveRole_RoleAlreadyExists() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));

        assertThrows(RoleAlreadyExistsException.class, () -> roleService.saveRole(role));
    }

    @Test
    void testSaveRoleWithUsers() {
        Role role = new Role();
        role.setName("ROLE_USER");

        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        role.setUsers(List.of(user1, user2));

        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user1);

        Role savedRole = roleService.saveRoleWithUsers(role);

        assertNotNull(savedRole);
        assertEquals("ROLE_USER", savedRole.getName());
        assertNotNull(savedRole.getUsers());
        assertEquals(2, savedRole.getUsers().size());
    }
}
