package com.example.googledrive.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Data Transfer Object (DTO) for updating a user.
 * This class represents the data that can be updated for a user, including optional new username and email.
 *
 * The class is annotated with Jakarta Bean Validation annotations to enforce constraints on the input.
 * It is also annotated with Lombok annotations to automatically generate getter and setter methods.
 */
@Getter
@Setter
public class UpdateUserDto {
    private Optional<String> username;
    private Optional<String> email;

}
