package com.andrea.eleves_note.exception;

import com.andrea.eleves_note.model.AppiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;

@ControllerAdvice
public class CustomException {

    @ExceptionHandler(StudentNotFountException.class)

    public ResponseEntity<AppiError> handleStudentNotFound(StudentNotFountException e){
        return builedResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistStudent.class)
    public ResponseEntity<AppiError> personneExistante(ExistStudent e){

        return builedResponse(e.getMessage(), HttpStatus.CONFLICT);


    }

    @ExceptionHandler(ExistFiliere.class)
    public ResponseEntity<AppiError> filiereExistante(ExistFiliere e){
        return builedResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FiliereNotFound.class)
    public ResponseEntity<AppiError> filiereInexistante(FiliereNotFound e){
        return builedResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MatiereNotFound.class)
    public ResponseEntity<AppiError> matiereInexistante(MatiereNotFound e){
        return builedResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoteNotFount.class)
    public ResponseEntity<AppiError> noteNoteFoundHandle(NoteNotFount e){
        return builedResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<AppiError> builedResponse(String message, HttpStatus status){
        AppiError error = new AppiError();

        error.setMessage(message);
        error.setCode(status.value());
        error.setDateTime(LocalDateTime.now());

        return new ResponseEntity<>(error, status);

    }





}
