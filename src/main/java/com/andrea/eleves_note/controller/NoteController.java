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

    @GetMapping("get/all")
    public ResponseEntity<List<Note>> getAll(){
        List<Note> note = noteService.getAll();
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveNote(@RequestBody Note note){
        Note note1 = noteService.saveNote(note);
        return new ResponseEntity<>(note1, HttpStatus.CREATED);
    }

    @PostMapping("/all")
    public ResponseEntity<?> saveAllNote(@RequestBody List<Note> notes){
        List<Note> note = noteService.saveAllNote(notes);
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
        return new ResponseEntity<>("Note supprimé avec succès", HttpStatus.OK);
    }
}
