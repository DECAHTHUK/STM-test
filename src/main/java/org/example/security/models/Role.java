package org.example.security.models;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    BUYER("BUYER");

    private final String role;

    @Override
    public String getAuthority() {
        return role;
    }

}