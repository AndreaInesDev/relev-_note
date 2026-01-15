package com.andrea.eleves_note.service;

import com.andrea.eleves_note.exception.ExistStudent;
import com.andrea.eleves_note.exception.FiliereNotFound;
import com.andrea.eleves_note.exception.StudentNotFountException;
import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Student;
import com.andrea.eleves_note.ripository.FiliereRepository;
import com.andrea.eleves_note.ripository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentList;
    private final FiliereRepository filiereRepository;

    public List<Student> getAll() {
        return studentList.findAll();
    }

    public Student saveStudent(Student student) {
        if (studentList.existsByMatricule(student.getMatricule())) {
            throw new ExistStudent("Cet etudiant de matricule " + student.getMatricule() + " existe deja");
        }

      /*  if (student.getFiliere() == null || student.getFiliere().getId() == null) {
            throw new FiliereNotFound("La filière est obligatoire pour enregistrer un étudiant.");
        }


        Filiere filiere = filiereRepository.findById(student.getFiliere().getId())
                .orElseThrow(() -> new FiliereNotFound("La filière spécifiée n'existe pas."));


        student.setFiliere(filiere);*/

        if (student.getMatricule().length() != 8){
            throw new RuntimeException("Le matricule doit avoir 8 charactères");
        }
        return studentList.save(student);
    }

    public void deleteStudent(String matricule) {
        Student student = studentList.findByMatricule(matricule);

        if (student == null) {
            throw new StudentNotFountException("Cet etudiant n'existe pas");
        }
        studentList.delete(student);
    }

    public Student updateStudent(String matricule, Student student) {
        Student student1 = studentList.findByMatricule(matricule);
        if (student1 == null) {
            throw new StudentNotFountException("Cet etudiant n'existe pas");
        }

        student1.setName(student.getName());
        student1.setUsername(student.getUsername());
        student1.setAddress(student.getAddress());

       /* if (student.getFiliere() != null && student.getFiliere().getId() != null) {
            Filiere newFiliere = filiereRepository.findById(student.getFiliere().getId())
                    .orElseThrow(() -> new FiliereNotFound("Impossible de mettre à jour : la nouvelle filière n'existe pas."));
            student1.setFiliere(newFiliere);

        }*/

        return studentList.save(student1);

    }
}
