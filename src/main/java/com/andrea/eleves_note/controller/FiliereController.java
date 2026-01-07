package com.andrea.eleves_note.controller;


import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.service.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filiere")
@RequiredArgsConstructor
public class FiliereController {
    private  final FiliereService filiereService;

    @GetMapping
    public ResponseEntity<List<Filiere>> getAllFiliere(){
        return new ResponseEntity<>(filiereService.getAllFiliere(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFiliere(@PathVariable Long id){
        return new ResponseEntity<>(filiereService.getFiliere(id), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<?> saveFiliere(@RequestBody Filiere filiere){
        return new ResponseEntity<>(filiereService.saveFiliere(filiere), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteFiliere(@PathVariable Long id){
        filiereService.deleteFiliere(id);
        return new ResponseEntity<>("La filiere a été supprimée", HttpStatus.OK);
    }

}