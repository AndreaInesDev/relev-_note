package com.andrea.eleves_note.controller;


import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Matiere;
import com.andrea.eleves_note.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/matiere")
public class MatiereController {
    private  final MatiereService matiereService;

    @GetMapping(path = "/find/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Matiere>> getAllMatiere(){
        List<Matiere> lis = matiereService.getAllMatiere();

        return new ResponseEntity<>(lis, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Matiere> getMatiereById(@PathVariable Long id){
        Matiere matiere = matiereService.getMatiereById(id);
        return new ResponseEntity<>(matiere, HttpStatus.OK);
    }

    @GetMapping("/matiereParFiliere/{id}")
    public ResponseEntity<?> matiereParFiliere(@PathVariable Long id){
        List<Matiere> matiereList = matiereService.listeMatiere(id);
        return new ResponseEntity<>(matiereList, HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Matiere> saveMatiere(@RequestBody Matiere matiere){
        Matiere matiere1 = matiereService.saveMatiere(matiere);
        return new ResponseEntity<>(matiere1, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<?> saveAllMatiere(@RequestBody List<Matiere> matieres){
        List<Matiere> matiere = matiereService.saveAllMatiere(matieres);
        return new ResponseEntity<>(matiere, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMatiere(@PathVariable Long id){
         matiereService.deleteMatiere(id);
        return new ResponseEntity<>("Matiere supprimée avec succès", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Matiere> updateMatiere(@PathVariable Long id, @RequestBody Matiere matiere ){
        Matiere matiere1 = matiereService.updateMatiere(id, matiere);
        return new ResponseEntity<>(matiere1, HttpStatus.CREATED);
    }

    @PostMapping("/inscriptionFiliere/{id1}/{id2}")
    public ResponseEntity<?> inscription(@PathVariable Long id1, @PathVariable Long id2){
        Matiere matiere = matiereService.inscriptionMatiereFiliere(id1, id2);
        return  new ResponseEntity<>(matiere, HttpStatus.CREATED);
    }

}
