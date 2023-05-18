package com.rogerli.springmall.repository;

import com.rogerli.springmall.entity.Product;
import com.rogerli.springmall.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaDao extends JpaRepository<User,Integer> {
    User findByUserId(Integer userId);

    User findByEmail(String email);
}
