package com.example.googledrive.exception;

public class FileAlreadyExistsException extends RuntimeException{
    public FileAlreadyExistsException(String userId, String folderId) {
        super("File already exists for the user in the folder");
    }
}
