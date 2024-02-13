package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Respository.RoleRepository;
import com.example.demo.Respository.UserRepository;
import com.example.demo.Service.RoleService;
import com.example.demo.Service.UserService;
import com.example.demo.handling.RoleAlreadyExistsException;
import com.example.demo.handling.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userService;


    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userService) {
        this.roleRepository = roleRepository;
        this.userService=userService;
    }

    @Override
    public Role saveRole(Role role, List<User> users) {
        role.setUsers(users);
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with name: " + name));
    }

    @Override
    @Transactional
    public Role saveRole(Role defaultRole) {
        // Check if the role with the given name already exists
        Optional<Role> existingRoleOptional = roleRepository.findByName(defaultRole.getName());
        if (existingRoleOptional.isPresent()) {
            // Role with the same name already exists, throw an exception
            throw new RoleAlreadyExistsException("Role with name '" + defaultRole.getName() + "' already exists");
        }

        // Save the role
        return roleRepository.save(defaultRole);
    }


    @Override
    @Transactional
    public Role saveRoleWithUsers(Role roleRequest) {



        // Save the Role entity to generate an ID
        Role savedRole = roleRepository.save(roleRequest);

        // Associate users with the saved role
        for (User user : roleRequest.getUsers()) {
            user.setRole(savedRole);
            userService.save(user);
        }

        // Update the savedRole with the associated users
        savedRole.setUsers(roleRequest.getUsers());

        return savedRole;
    }




}
    // Additional methods implementation
