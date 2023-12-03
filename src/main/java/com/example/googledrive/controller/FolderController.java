package com.example.googledrive.controller;

import com.example.googledrive.dto.CreateFolderDto;
import com.example.googledrive.dto.UpdateFolderDto;
import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.service.FolderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */

    @PostMapping("/folder/create")
    public ResponseEntity<Folder> createFolder(@Valid @RequestBody CreateFolderDto dto) {
        //User authenticatedUser =
        Folder folder = folderService.createFolder(dto);
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }

    @GetMapping("/folder/all")
    public ResponseEntity<List<Folder>> getAllFolders() {
        return new ResponseEntity<>(folderService.findAllFolders(), HttpStatus.OK);
    }

    @GetMapping("/folder/{id}")
    public ResponseEntity<Folder> getFolderById(@PathVariable String id) {
        Folder folder = folderService.getFolderById(id);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    //Test
    @GetMapping("/folder/{id}/files")
    public ResponseEntity<List<File>> getFilesForFolder(@PathVariable String id) {
        List<File> files = folderService.getFilesForFolder(id);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/folder/search/{search}")
    public ResponseEntity<List<Folder>> searchByFolderName(@PathVariable String search) {
        List<Folder> folder = folderService.searchByFolderName(search);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PutMapping("/folder/update/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable String id, @Valid @RequestBody UpdateFolderDto dto) {
        Folder folder = folderService.updateFolder(id, dto);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @DeleteMapping("/folder/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFolder(@PathVariable String id) {
        folderService.deleteFolder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
