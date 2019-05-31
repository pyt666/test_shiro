package com.example.test_shiro.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName Role
 * @Description TODO
 * @Author pyt
 * @Date 2019/5/22 9:55
 * @Version
 */
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roleName;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "role")
    private List<Permission> permissions;
}
