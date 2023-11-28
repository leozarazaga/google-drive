package com.example.googledrive.controller;

import com.example.googledrive.dto.CreateFileDto;
import com.example.googledrive.entity.File;
import com.example.googledrive.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */

    @PostMapping("/file/create/{userId}/folder/{folderId}")
    public ResponseEntity<File> createFileInFolder(@PathVariable String userId, @PathVariable String folderId, @RequestBody CreateFileDto fileDto) {
        File createdFile = fileService.createFileInFolder(userId, folderId, fileDto);
        return new ResponseEntity<>(createdFile, HttpStatus.CREATED);
    }

    @GetMapping("/file/all")
    public ResponseEntity<List<File>> getAllFiles(){
        List<File> files = fileService.getAllFiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

}
