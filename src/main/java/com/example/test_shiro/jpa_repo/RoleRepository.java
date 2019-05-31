package com.example.test_shiro.jpa_repo;

import com.example.test_shiro.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
