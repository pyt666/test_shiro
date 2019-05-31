package com.example.test_shiro.entity;

import lombok.*;
import org.springframework.ui.Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName User
 * @Description TODO
 * @Author pyt
 * @Date 2019/5/21 17:04
 * @Version
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    private String salt;
    private String credentialsSalt;
    private String password;
    private Byte status;
    private String phone;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Role> roles;
}
