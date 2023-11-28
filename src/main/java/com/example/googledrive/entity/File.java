package com.example.googledrive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor

@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String fileName;

    @Lob
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;


    public File(String fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }
}
