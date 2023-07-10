package com.rogerli.springmall.model;

import com.rogerli.springmall.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserIdentity {

    private final SecureUser EMPTY_USER = new SecureUser(new User());

    private SecureUser getSecureUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return principal.equals("anonymousUser")
                ? EMPTY_USER
                : (SecureUser) principal;
    }

    public boolean isAnonymous() {
        return EMPTY_USER.equals(getSecureUser());
    }

    public Integer getId() {
        return getSecureUser().getUserId();
    }

    public String getEmail() {
        return getSecureUser().getUsername();
    }
}
