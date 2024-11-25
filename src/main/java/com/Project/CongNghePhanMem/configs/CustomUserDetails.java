package com.Project.CongNghePhanMem.configs;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Project.CongNghePhanMem.Entity.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = -4452060929118917376L;
	private User user;

   
    public CustomUserDetails(User user) {
    	super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + user.getRole().toUpperCase(); 
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Arrays.asList(authority);
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

        return user.isAccounNonLocked(); 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true; 
    }

    @Override
    public boolean isEnabled() {
       
        return user.isEnabled();
    }
    
}
