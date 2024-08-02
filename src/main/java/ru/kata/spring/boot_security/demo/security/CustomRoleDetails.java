package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public class CustomRoleDetails implements GrantedAuthority {

    private final transient Role role;

    public CustomRoleDetails(Role role) {
        this.role = role;
    }


    @Override
    public String getAuthority() {
        return role.getName();
    }
}
