package com.andrea.eleves_note.controller;

import com.andrea.eleves_note.model.Student;
import com.andrea.eleves_note.service.NoteService;
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

    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody Student student){
        Student student1 = studentService.saveStudent(student);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);

    }

    @PostMapping("/inscription/{id1}/{id2}")
    public  ResponseEntity<?> inscription(@PathVariable Long id1,
                                               @PathVariable Long id2){
        boolean student = studentService.inscriptionStudent(id1, id2);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>("l'etudiant a été supprimé avec succès", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student){
        Student student1 = studentService.updateStudent(id, student);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);
    }


}