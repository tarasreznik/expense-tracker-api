package com.example.ExpanseTrackerAPI.controller;

import com.example.ExpanseTrackerAPI.entity.AuthModel;
import com.example.ExpanseTrackerAPI.entity.User;
import com.example.ExpanseTrackerAPI.entity.UserModel;
import com.example.ExpanseTrackerAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody AuthModel authModel){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(HttpStatus.OK);


    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
}
