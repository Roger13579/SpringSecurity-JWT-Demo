package com.rogerli.springmall.repository;

import com.rogerli.springmall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaDao extends JpaRepository<User,Integer> {
    User findByUserId(Integer userId);

    User findByEmail(String email);
}
