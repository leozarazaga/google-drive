package com.example.googledrive.controller;

import com.example.googledrive.dto.CreateFolderDto;
import com.example.googledrive.dto.UpdateFolderDto;
import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import com.example.googledrive.service.FolderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller for handling folder-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting folders.
 *
 * The class is annotated with Spring's RestController, indicating that it handles RESTful API requests.
 * Each method is documented to describe its purpose and functionality.
 */
@RestController
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    /**
     * Endpoint for creating a new folder.
     *
     * @param dto The CreateFolderDto containing the folder name.
     * @return ResponseEntity containing the created folder and HttpStatus.CREATED.
     */
    @PostMapping("/folder/create")
    public ResponseEntity<Folder> createFolder(@Valid @RequestBody CreateFolderDto dto) {
        Folder folder = folderService.createFolder(dto);
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }

    /**
     * Endpoint for retrieving all folders associated with the authenticated user.
     *
     * @param user The authenticated user obtained from the security context.
     * @return ResponseEntity containing a list of folders and HttpStatus.OK.
     */
    @GetMapping("/folder/all")
    public ResponseEntity<List<Folder>> retrieveAllFoldersByUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(folderService.getAllFoldersByUser(user), HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving a folder by its ID.
     *
     * @param id   The ID of the folder to be retrieved.
     * @param user The authenticated user obtained from the security context.
     * @return ResponseEntity containing the retrieved folder and HttpStatus.OK.
     */
    @GetMapping("/folder/{id}")
    public ResponseEntity<Folder> getFolderById(@PathVariable String id, @AuthenticationPrincipal User user) {
        Folder folder = folderService.getFolderById(id, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving all files from a folder by its ID.
     *
     * @param id   The ID of the folder containing the files.
     * @param user The authenticated user obtained from the security context.
     * @return ResponseEntity containing a list of files in the folder and HttpStatus.OK.
     */
    @GetMapping("/folder/{id}/files")
    public ResponseEntity<List<File>> getFilesFromFolder(@PathVariable String id, @AuthenticationPrincipal User user) {
        List<File> files = folderService.getFilesFromFolder(id, user);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    /**
     * Endpoint for searching folders by name.
     *
     * @param search The search query for folder names.
     * @param user   The authenticated user obtained from the security context.
     * @return ResponseEntity containing a list of matching folders and HttpStatus.OK.
     */
    @GetMapping("/folder/search/{search}")
    public ResponseEntity<List<Folder>> searchByFolderName(@PathVariable String search, @AuthenticationPrincipal User user) {
        List<Folder> folder = folderService.searchByFolderName(search, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    /**
     * Endpoint for updating a folder by its ID.
     *
     * @param id   The ID of the folder to be updated.
     * @param dto  The UpdateFolderDto containing optional folder name updates.
     * @param user The authenticated user obtained from the security context.
     * @return ResponseEntity containing the updated folder and HttpStatus.OK.
     */
    @PutMapping("/folder/update/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable String id, @Valid @RequestBody UpdateFolderDto dto, @AuthenticationPrincipal User user) {
        Folder folder = folderService.updateFolder(id, dto, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    /**
     * Endpoint for deleting a folder by its ID.
     *
     * @param id   The ID of the folder to be deleted.
     * @param user The authenticated user obtained from the security context.
     * @return ResponseEntity with a HttpStatus indicating success (NO_CONTENT).
     */
    @DeleteMapping("/folder/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFolder(@PathVariable String id, @AuthenticationPrincipal User user) {
        folderService.deleteFolder(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
