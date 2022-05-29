package com.pfa.app.config;

import java.util.*;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pfa.app.models.Role;
import com.pfa.app.models.User;

public class MyUserDetails implements UserDetails {
	 
    private User user;
     
    public MyUserDetails(User user) {
        this.user = user;
    }
 
    @Override
    public List <? extends GrantedAuthority> getAuthorities() {
        List <Role> roles = user.getRoles();
        List <SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
        	System.out.println("role: " + role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
         
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getUsername();
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