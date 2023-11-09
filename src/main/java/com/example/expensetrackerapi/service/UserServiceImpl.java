package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.User;
import com.example.expensetrackerapi.entity.UserModel;
import com.example.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import com.example.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.example.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    private final PasswordEncoder bcryptEncoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder bcryptEncoder) {
        this.userRepo = userRepo;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public User createUser(UserModel user) {
        if (Boolean.TRUE.equals(userRepo.existsByEmail(user.getEmail()))) {
            throw new ItemAlreadyExistsException("User with email " + user.getEmail() + " is already exists");
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepo.save(newUser);
    }

    @Override
    public User readUser() {
        Long userId = getLoggedInUser().getId();
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for id:" + userId));
    }

    @Override
    public User updateUser(UserModel user){
        User existingUser = readUser();
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User existingUser = readUser();
        userRepo.delete(existingUser);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }
}
