package com.example.test_shiro.service;

import com.example.test_shiro.entity.Role;
import com.example.test_shiro.entity.User;

import java.util.Map;

public interface LoginService {
    User addUser(Map<String, Object> map);
    Role addRole(Map<String, Object> map);
    User findByUsername(String name);
}
