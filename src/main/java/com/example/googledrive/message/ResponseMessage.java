package com.example.googledrive.message;

import lombok.Getter;
import lombok.Setter;

/**
 * ResponseMessage class representing a simple message for API responses.
 * Utilizes Lombok annotations for automatic generation of getters and setters.
 */
@Getter
@Setter
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

}
