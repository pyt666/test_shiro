package com.example.test_shiro.service.ServiceImpl;

import com.example.test_shiro.entity.Permission;
import com.example.test_shiro.entity.Role;
import com.example.test_shiro.entity.User;
import com.example.test_shiro.jpa_repo.RoleRepository;
import com.example.test_shiro.jpa_repo.UserRepository;
import com.example.test_shiro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName LohinServiceImpl
 * @Description TODO
 * @Author pyt
 * @Date 2019/5/22 10:15
 * @Version
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    //添加用户
    @Override
    public User addUser(Map<String, Object> map) {
        User user = new User();
        user.setPassword(map.get("password").toString());
        user.setUsername(map.get("username").toString());
        userRepository.save(user);
        return user;
    }
    //添加角色
    @Override
    public Role addRole(Map<String, Object> map) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(map.get("user_id").toString()));
        if (userOptional.isPresent()){
            Role role = new Role();
            role.setRoleName(map.get("role_name").toString());
            role.setUser(userOptional.get());
            Permission permission1 = new Permission();
            permission1.setPermission("create");
            permission1.setRole(role);
            Permission permission2 = new Permission();
            permission2.setPermission("update");
            permission2.setRole(role);
            List<Permission> permissions = new ArrayList<Permission>();
            permissions.add(permission1);
            permissions.add(permission2);
            role.setPermissions(permissions);
            roleRepository.save(role);
            return role;
        }else{
            return null;
        }
    }
    //查询用户通过用户名
    @Override
    public User findByUsername(String name) {
        Optional<User> userOptional = userRepository.findByUsername(name);
        if (userOptional.isPresent()){
            return userOptional.get();
        }else{
            return null;
        }
    }
}
