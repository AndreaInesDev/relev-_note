package com.andrea.eleves_note.controller;


import com.andrea.eleves_note.model.Note;
import com.andrea.eleves_note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAll(){
        return new ResponseEntity<>(noteService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveNote(@RequestBody Note note){
        return new ResponseEntity<>(noteService.saveNote(note), HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> saveAllNote(@RequestBody List<Note> notes){
        return new ResponseEntity<>(noteService.saveAllNote(notes), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id){
        return new ResponseEntity<>(noteService.deleteNote(id), HttpStatus.OK);
    }
}
