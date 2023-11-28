package com.example.googledrive.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFileDto {

    private String fileName;
    private byte[] content;
}
