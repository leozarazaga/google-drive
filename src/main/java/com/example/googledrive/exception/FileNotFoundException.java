package com.example.googledrive.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String id) {
        super("A file with id '" + id + "' does not exist");

    }
}
