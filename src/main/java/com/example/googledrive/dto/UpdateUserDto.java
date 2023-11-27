package com.example.googledrive.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class UpdateUserDto {
    private Optional<String> username;
    private Optional<String> email;
}
