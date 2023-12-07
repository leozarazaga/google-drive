package com.example.googledrive.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Data Transfer Object (DTO) for updating a folder.
 * This class represents the data that can be updated for a folder, including an optional new folder name.
 *
 * The class is annotated with Lombok annotations to automatically generate getter and setter methods.
 */
@Getter
@Setter
public class UpdateFolderDto {
    private Optional<String> folderName;
}
