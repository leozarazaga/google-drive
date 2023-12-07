package com.example.googledrive.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a new folder.
 * This class represents the data needed to create a folder, including the folder name.
 *
 * The class is annotated with Lombok annotations to automatically generate getter and setter methods.
 * It also includes Jackson annotation to exclude null fields from the JSON representation.
 */
@Getter
@Setter
public class CreateUserDto {
    private String username;
    private String password;
    private String email;

}
