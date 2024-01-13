package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.entity.UserModel;
import com.example.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.example.expensetrackerapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    private final PasswordEncoder bcryptEncoder;

    public User readUser() {
        Long userId = getLoggedInUser().getId();
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for id:" + userId));
    }

    public User updateUser(UserModel user){
        User existingUser = readUser();
        existingUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : existingUser.getFirstName());
        existingUser.setLastName(user.getLastName() != null ? user.getLastName() : existingUser.getLastName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        return userRepo.save(existingUser);
    }

    public void deleteUser() {
        User existingUser = readUser();
        userRepo.delete(existingUser);
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }
}
