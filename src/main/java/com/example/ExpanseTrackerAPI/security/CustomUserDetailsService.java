package com.example.ExpanseTrackerAPI.security;

import com.example.ExpanseTrackerAPI.entity.User;
import com.example.ExpanseTrackerAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User exsistingUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));

        return new org.springframework.security.core.userdetails.User(exsistingUser.getEmail(), exsistingUser.getPassword(), new ArrayList<>());

    }
}