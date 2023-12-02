package com.wot.security;

import com.wot.entitites.Client;
import com.wot.entitites.Manager;
import com.wot.entitites.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Client client;
    private Manager manager;

    public CustomUserDetails(Client client) {
        super();
        this.client = client;

    }

    public CustomUserDetails(Manager manager) {
        super();
        this.manager = manager;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (client != null) {
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
            for (Roles roles2 : client.getRoles()) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(roles2.getName()));
            }
            return simpleGrantedAuthorities;
        } else {
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
            for (Roles roles2 : manager.getRoles()) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(roles2.getName()));
            }
            return simpleGrantedAuthorities;
        }

    }

    @Override
    public String getPassword() {
        if (client != null) {
            return client.getPassword();
        } else {
            return manager.getPassword();
        }
    }

    @Override
    public String getUsername() {

        if (client != null) {
            return client.getEmail();
        } else {
            return manager.getEmail();
        }
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

}
