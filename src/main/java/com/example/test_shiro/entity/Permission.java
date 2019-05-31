package com.example.test_shiro.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @ClassName Permission
 * @Description TODO
 * @Author pyt
 * @Date 2019/5/22 9:56
 * @Version
 */
@Entity
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String permission;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
}
