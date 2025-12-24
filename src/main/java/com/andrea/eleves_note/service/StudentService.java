package com.andrea.eleves_note.service;

import com.andrea.eleves_note.exception.ExistStudent;
import com.andrea.eleves_note.exception.StudentNotFountException;
import com.andrea.eleves_note.model.Student;
import com.andrea.eleves_note.ripository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentList;

    public List<Student> getAll(){
        return studentList.findAll();
    }

    public Student saveStudent(Student student){
        if (studentList.existsByMatricule(student.getMatricule())){
            throw  new ExistStudent("Cet etudiant de matricule " + student.getMatricule() + " existe deja");
        }

        return studentList.save(student);
    }

    public  void deleteStudent(String matricule){
        Student student = studentList.findByMatricule(matricule);

        if (student == null){
            throw new StudentNotFountException("Cet etudiant n'existe pas");
        }
        studentList.delete(student);
    }

}
