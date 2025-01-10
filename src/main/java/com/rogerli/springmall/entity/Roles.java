package com.rogerli.springmall.entity;

import com.rogerli.springmall.constant.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Enumerated(EnumType.STRING)
    private UserAuthority roleName;
}
