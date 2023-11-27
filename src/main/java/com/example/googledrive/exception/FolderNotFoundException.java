package com.example.googledrive.exception;

public class FolderNotFoundException extends RuntimeException{
    public FolderNotFoundException(String id) {
        super("A folder with id '" + id + "' does not exist");
    }
}
