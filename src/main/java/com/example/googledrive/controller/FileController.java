package com.example.googledrive.controller;

import com.example.googledrive.entity.File;
import com.example.googledrive.message.ResponseFile;
import com.example.googledrive.message.ResponseMessage;
import com.example.googledrive.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */

    @PostMapping("/file/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("folderId") String folderId, @RequestParam("userId") String userId) {
        try {
            fileService.store(file, folderId, userId);
            String successMessage = "Uploaded the file successfully: " + file.getOriginalFilename();
            return new ResponseEntity<>(new ResponseMessage(successMessage), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Could not upload file: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(errorMessage));
        }
    }


    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = fileService.getAllFiles().stream().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getFolder() != null ? dbFile.getFolder().getFolderName() : null,
                    dbFile.getUser() != null ? dbFile.getUser().getUsername() : null);

        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        File fileDB = fileService.getFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileDB.getName());
        return new ResponseEntity<>(fileDB.getData(), headers, HttpStatus.OK);
    }


}
