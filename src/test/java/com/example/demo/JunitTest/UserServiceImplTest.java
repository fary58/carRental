package com.example.demo.JunitTest;

import com.example.demo.Entity.User;
import com.example.demo.Entity.Vehicle;
import com.example.demo.Respository.UserRepository;
import com.example.demo.Respository.VehicleRepository;
import com.example.demo.Service.UserService;
import com.example.demo.ServiceImpl.UserServiceImpl;
import com.example.demo.handling.UsernameExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setUser_id(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getUser_id());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User retrievedUser = userService.getUserByUsername("testUser");

        assertNotNull(retrievedUser);
        assertEquals("testUser", retrievedUser.getUsername());
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userRepository.findAll()).thenReturn(userList);

        List<User> retrievedUsers = userService.getAllUsers();

        assertNotNull(retrievedUsers);
        assertEquals(userList.size(), retrievedUsers.size());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUser_id(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(user);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUsernameExists() {
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        assertThrows(UsernameExistsException.class, () -> userService.usernameExists("existingUser"));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User retrievedUser = userService.findByUsername("testUser");

        assertNotNull(retrievedUser);
        assertEquals("testUser", retrievedUser.getUsername());
    }

    @Test
    void testSaveUserWithVehicles() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setMake("Toyota");
        vehicle1.setModel("Camry");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setMake("Honda");
        vehicle2.setModel("Civic");

        user.setVehicles(List.of(vehicle1, vehicle2));

        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUserWithVehicles(user);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertNotNull(savedUser.getVehicles());
        assertEquals(2, savedUser.getVehicles().size());
    }
}
