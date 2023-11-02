package com.epam.trainning.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    VIEW_INFO, VIEW_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}