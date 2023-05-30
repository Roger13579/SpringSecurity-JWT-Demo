package com.rogerli.springmall.repository;

import com.rogerli.springmall.constant.UserAuthority;
import com.rogerli.springmall.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<Roles,Integer> {
    Roles findByRoleName(UserAuthority userAuthority);
}
