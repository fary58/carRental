package com.example.demo.Service;

import com.example.demo.Entity.User;

import java.util.List;

public interface UserService {

        User saveUser(User user);

        User getUserById(Long userId);

        User getUserByUsername(String username);

        List<User> getAllUsers();

        boolean usernameExists(String username);
        User findByUsername(String username);

        void deleteUser(Long userId);

        void updateUser(User user);

      void   username_Exists(String username);

        User saveUserWithVehicles(User user);



}
