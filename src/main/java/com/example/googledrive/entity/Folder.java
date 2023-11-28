package com.example.googledrive.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor

@Table(name = "folders")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Folder name cannot be blank")
    private String folderName;

    public Folder(String folderName) {
        this.folderName = folderName;
    }
}
