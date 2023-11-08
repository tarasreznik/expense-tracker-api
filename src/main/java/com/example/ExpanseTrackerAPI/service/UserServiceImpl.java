package com.example.ExpanseTrackerAPI.service;

import com.example.ExpanseTrackerAPI.entity.User;
import com.example.ExpanseTrackerAPI.entity.UserModel;
import com.example.ExpanseTrackerAPI.exceptions.ItemAlreadyExistsException;
import com.example.ExpanseTrackerAPI.exceptions.ResourceNotFoundException;
import com.example.ExpanseTrackerAPI.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User createUser(UserModel user) {
        if(userRepo.existsByEmail(user.getEmail())){
            throw new ItemAlreadyExistsException("User with email " + user.getEmail() + " is already exists");
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepo.save(newUser);
    }

    @Override
    public User readUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for id:" + id));
    }

    @Override
    public User updateUser(UserModel user, Long id) {
        User existingUser = readUser(id);
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = readUser(id);
        userRepo.delete(existingUser);
    }
}
