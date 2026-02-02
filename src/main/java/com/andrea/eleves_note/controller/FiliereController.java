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

    @GetMapping("get/all")
    public ResponseEntity<List<Filiere>> getAllFiliere(){
        return new ResponseEntity<>(filiereService.getAllFiliere(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFiliere(@PathVariable Long id){
        Filiere filiere = filiereService.getFiliere(id);
        return new ResponseEntity<>(filiere, HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<?> saveFiliere(@RequestBody Filiere filiere){
        Filiere filiere1 = filiereService.saveFiliere(filiere);
        return new ResponseEntity<>(filiere1, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> deleteFiliere(@PathVariable Long id){
        filiereService.deleteFiliere(id);
        return new ResponseEntity<>("La filiere a été supprimée", HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public  ResponseEntity<Filiere> updateFiliere(Long id, Filiere filiere){
        Filiere filiere1 = filiereService.updateFiliere(id, filiere);
        return new ResponseEntity<>(filiere1, HttpStatus.CREATED);
    }

}