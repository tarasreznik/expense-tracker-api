package com.example.ExpanseTrackerAPI.service;

import com.example.ExpanseTrackerAPI.entity.User;
import com.example.ExpanseTrackerAPI.entity.UserModel;

public interface UserService {
    User createUser(UserModel user);

    User readUser(Long id);

    User updateUser(UserModel user, Long id);

    void deleteUser(Long id);
}
