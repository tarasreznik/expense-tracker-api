package com.example.expensetrackerapi.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @NotBlank(message = "name should not be empty")
    private String name;

    @NotNull(message = "Email should not be empty")
    @Email(message = "Enter a valid email")
    String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 5, message = "Password should be at least 5 characters")
    String password;

    private Long age = 0L;
}
