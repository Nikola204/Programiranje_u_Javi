package com.example.musicapp.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private com.example.musicapp.model.User user;

    public UserDetails(com.example.musicapp.model.User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return user.getFirstname() + " " + user.getLastname();
    }

    public com.example.musicapp.model.User getUser() {
        return user;
    }

    public String getFirstName() {
        return user.getFirstname();
    }

    public void setUser(com.example.musicapp.model.User user) {
        this.user = user;
    }
}