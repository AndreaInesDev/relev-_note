package com.andrea.eleves_note.controller;


import com.andrea.eleves_note.model.Matiere;
import com.andrea.eleves_note.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/matiere")
public class MatiereController {
    private  final MatiereService matiereService;

    @GetMapping
    public ResponseEntity<List<Matiere>> getAllMatiere(){
        return new ResponseEntity<>(matiereService.getAllMatiere(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Matiere> saveMatiere(@RequestBody Matiere matiere){
        return new ResponseEntity<>(matiereService.saveMatiere(matiere), HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<?> saveAllMatiere(@RequestBody List<Matiere> matieres){
        return new ResponseEntity<>(matiereService.saveAllMatiere(matieres), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMatiere(@PathVariable Long id){
        return new ResponseEntity<>(matiereService.deleteMatiere(id), HttpStatus.OK);
    }

}
