package com.example.googledrive.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a file in the system.
 * Each file has a unique identifier, name, type, and associated data.
 * Files are associated with a user and optionally with a folder.
 * The class is annotated with JPA annotations to specify its persistence in the database.
 *
 * @Entity - Indicates that this class is a JPA entity.
 * @Table - Specifies the name of the database table to which this entity is mapped.
 * @Getter - Lombok annotation to auto-generate getter methods for all fields.
 * @Setter - Lombok annotation to auto-generate setter methods for all fields.
 */
@Entity
@Getter
@Setter
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    private String type;
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public File() {
    }

    public File(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}

