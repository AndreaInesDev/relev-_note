package com.andrea.eleves_note.service;

import com.andrea.eleves_note.exception.ExistStudent;
import com.andrea.eleves_note.exception.FiliereNotFound;
import com.andrea.eleves_note.exception.StudentNotFountException;
import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Note;
import com.andrea.eleves_note.model.Student;
import com.andrea.eleves_note.ripository.FiliereRepository;
import com.andrea.eleves_note.ripository.MatiereRepository;
import com.andrea.eleves_note.ripository.NoteRepository;
import com.andrea.eleves_note.ripository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentList;
    private final FiliereRepository filiereRepository;
    private final MatiereRepository matiereRepository;
    private final NoteRepository noteRepository;

    public List<Student> getAll() {
        return studentList.findAll();
    }

    public Student saveStudent(Student student) {
        if (studentList.existsByMatricule(student.getMatricule())) {
            throw new ExistStudent("Cet etudiant de matricule " + student.getMatricule() + " existe deja");
        }

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

        return studentList.save(student1);

    }

    public Double moyenneEtudiant(Long id){
        Student student = studentList.findById(id)
                .orElseThrow(() -> new StudentNotFountException("Cet etudiant n'existe  pas"));

        List<Note> noteList = noteRepository.findByStudentId(id);

        if (noteList.isEmpty()){
            return 0.0 ;
        }

        Double somme = 0.0;

        for (int i = 0; i < noteList.size(); i++) {
            somme = somme + noteList.get(i).getValeur();
        }

        Double moyenne = somme / noteList.size();

        return  moyenne;


    }
}
