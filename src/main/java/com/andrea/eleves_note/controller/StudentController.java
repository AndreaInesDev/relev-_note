package com.andrea.eleves_note.controller;

import com.andrea.eleves_note.model.Student;
import com.andrea.eleves_note.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService ;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent(){
            return new ResponseEntity<>(studentService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody Student student){
           return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);

    }

    @DeleteMapping("/{matricule}")
    public ResponseEntity<?> deleteStudent(@PathVariable String matricule){
            studentService.deleteStudent(matricule);
            return new ResponseEntity<>("l'etudiant a été supprimé avec succès", HttpStatus.OK);
    }
}
