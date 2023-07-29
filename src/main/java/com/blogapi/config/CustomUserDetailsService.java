package com.blogapi.config;


import com.blogapi.entity.Role;
import com.blogapi.entity.User;
import com.blogapi.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws // when you pass the useranme and password this will go to CustomUserDetailsService class to the method loadUserByUsername then this method will check thus username andemail exist in db or not
            UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,
                        usernameOrEmail)   // entity user
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with " + usernameOrEmail));
        return new
                org.springframework.security.core.userdetails.User(user.getEmail(),     // spring security user
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
    private Collection< ? extends GrantedAuthority>
    mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}