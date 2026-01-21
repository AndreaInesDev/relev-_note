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

    @GetMapping(path = "/get/all")
    public ResponseEntity<List<Student>> getAllStudent(){
        return new ResponseEntity<>(studentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/moyenne/{id}")
    public ResponseEntity<?> calculeMoyenne(@PathVariable Long id){
        Double moyenne = studentService.moyenneEtudiant(id);

        return new ResponseEntity<>(moyenne, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody Student student){
        Student student1 = studentService.saveStudent(student);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{matricule}")
    public ResponseEntity<?> deleteStudent(@PathVariable String matricule){
        studentService.deleteStudent(matricule);
        return new ResponseEntity<>("l'etudiant a été supprimé avec succès", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student){
        Student student1 = studentService.updateStudent(id, student);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);
    }


}