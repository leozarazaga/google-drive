package com.example.googledrive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a folder in the system.
 * Each folder has a unique identifier, a name, and is associated with a user.
 * Folders can contain multiple files.
 *
 * The class is annotated with JPA annotations to specify its persistence in the database.
 *
 * @Entity - Indicates that this class is a JPA entity.
 * @Table - Specifies the name of the database table to which this entity is mapped.
 * @Getter - Lombok annotation to auto-generate getter methods for all fields.
 * @Setter - Lombok annotation to auto-generate setter methods for all fields.
 * @NoArgsConstructor - Lombok annotation to generate a no-args constructor.
 */
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<File> files;

    public Folder(String folderName) {
        this.folderName = folderName;
    }
}
