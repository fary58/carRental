package com.example.demo.ServiceImpl;

import com.example.demo.Entity.User;
import com.example.demo.Entity.Vehicle;
import com.example.demo.Respository.UserRepository;
import com.example.demo.Respository.VehicleRepository;
import com.example.demo.Service.UserService;
import com.example.demo.handling.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final VehicleRepository vehicleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public User saveUser(User user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void updateUser(User user) {
        if (user.getUser_id() != null) {
            User existingUser = userRepository.findById(user.getUser_id()).orElse(null);

            if (existingUser != null) {
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                existingUser.setRole(user.getRole());
                // Update other properties as needed

                userRepository.save(existingUser);
            }
        }
    }

    @Override
    public void username_Exists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameExistsException("User already exists with username: " + username);
        }
    }

    @Override
    public boolean usernameExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameExistsException("User already exists with username: " + username);
        }
        return false;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUserWithVehicles(User user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user with associated vehicles
        User savedUser = userRepository.save(user);

        // Set the saved user for each associated vehicle
        for (Vehicle vehicle : savedUser.getVehicles()) {
            vehicle.setUser(savedUser);
        }

        // Save the vehicles
        vehicleRepository.saveAll(savedUser.getVehicles());

        return savedUser;
    }
}
