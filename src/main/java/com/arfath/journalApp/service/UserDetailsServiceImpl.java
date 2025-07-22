package com.arfath.journalApp.service;

import com.arfath.journalApp.entity.User;
import com.arfath.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null){
            return org.springframework.security.core.userdetails.User.builder() //User is spring security one
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0])) //changed from this .roles(user.getRoles().toArray(new String[0])) to .roles(user.getRoles() != null ? user.getRoles().toArray(new String[0]) : new String[0]) //why this conversion cuz roles it takes comma separated values
                    .build();
        }
        throw  new UsernameNotFoundException("user not found with the username : "+username);
    }
}
