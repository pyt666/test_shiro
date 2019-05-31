package com.example.test_shiro.jpa_repo;

import com.example.test_shiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select a from User a where a.username = ?1")
    Optional<User> findByUsername(String username);
}
