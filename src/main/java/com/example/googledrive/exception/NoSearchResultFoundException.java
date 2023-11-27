package com.example.googledrive.exception;

public class NoSearchResultFoundException extends RuntimeException{
    public NoSearchResultFoundException(String search) {
        super("No search result found for the search '" + search + "'");
    }
}
