package com.example.demo.Service;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;

import java.util.List;

public interface RoleService {

    Role saveRole(Role role, List<User> users);

    Role getRoleById(Long roleId);

    List<Role> getAllRoles();

    Role findByName(String roleName);

    Role saveRole(Role defaultRole);
    Role saveRoleWithUsers(Role roleRequest);

    // Additional methods as needed
}