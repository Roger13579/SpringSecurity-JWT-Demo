package com.rogerli.springmall.config;

import com.rogerli.springmall.constant.UserAuthority;
import com.rogerli.springmall.entity.Roles;
import com.rogerli.springmall.repository.RoleJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {
    @Bean
    public CommandLineRunner loadRoles(RoleJpaRepository rolesRepository) {
        return args -> {
            if (rolesRepository.count() == 0) {
                rolesRepository.save(new Roles(null, UserAuthority.ADMIN));
                rolesRepository.save(new Roles(null, UserAuthority.NORMAL));
            }
        };
    }
}
