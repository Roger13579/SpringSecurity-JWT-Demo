package com.rogerli.springmall.entity;

import com.rogerli.springmall.constant.UserAuthority;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Enumerated(EnumType.STRING)
    private UserAuthority roleName;
}
