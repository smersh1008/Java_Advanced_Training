package com.epam.trainning.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    STANDARD;

    @Override
    public String getAuthority() {
        return name();
    }
}