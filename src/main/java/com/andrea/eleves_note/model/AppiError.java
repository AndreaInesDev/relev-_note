package com.andrea.eleves_note.model;


import lombok.Data;


import java.time.LocalDateTime;

@Data
public class AppiError {
    private int code;
    private String message;
    private LocalDateTime dateTime;
}
