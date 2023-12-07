package com.example.googledrive.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ErrorResponse class representing a detailed error response for API exceptions.
 * Utilizes Lombok annotations for automatic generation of getters and setters.
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YY HH:mm:ss")
    private LocalDateTime timestamp;

    private List<String> message;

    public ErrorResponse(List<String> message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
}
