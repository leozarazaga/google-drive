package com.example.googledrive.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a new file.
 * This class represents the data needed to create a file, including the file name and its content.
 * The class is annotated with Lombok annotations to automatically generate getter and setter methods.
 */
@Getter
@Setter
public class CreateFileDto {

    private String fileName;
    private byte[] content;

}
