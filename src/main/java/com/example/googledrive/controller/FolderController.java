package com.example.googledrive.controller;

import com.example.googledrive.dto.CreateFolderDto;
import com.example.googledrive.dto.UpdateFolderDto;
import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import com.example.googledrive.service.FolderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        Folder folder = folderService.createFolder(dto);
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }

    @GetMapping("/folder/all")
    public ResponseEntity<List<Folder>> retrieveAllFoldersByUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(folderService.getAllFoldersByUser(user), HttpStatus.OK);
    }

    @GetMapping("/folder/{id}")
    public ResponseEntity<Folder> getFolderById(@PathVariable String id, @AuthenticationPrincipal User user) {
        Folder folder = folderService.getFolderById(id, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }


    @GetMapping("/folder/{id}/files")
    public ResponseEntity<List<File>> getFilesFromFolder(@PathVariable String id, @AuthenticationPrincipal User user) {
        List<File> files = folderService.getFilesFromFolder(id, user);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }


    @GetMapping("/folder/search/{search}")
    public ResponseEntity<List<Folder>> searchByFolderName(@PathVariable String search, @AuthenticationPrincipal User user) {
        List<Folder> folder = folderService.searchByFolderName(search, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PutMapping("/folder/update/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable String id, @Valid @RequestBody UpdateFolderDto dto, @AuthenticationPrincipal User user) {
        Folder folder = folderService.updateFolder(id, dto, user);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @DeleteMapping("/folder/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFolder(@PathVariable String id, @AuthenticationPrincipal User user) {
        folderService.deleteFolder(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
