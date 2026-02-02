package com.andrea.eleves_note.service;

import com.andrea.eleves_note.exception.ExistStudent;
import com.andrea.eleves_note.exception.FiliereNotFound;
import com.andrea.eleves_note.exception.NoteNotFount;
import com.andrea.eleves_note.exception.StudentNotFountException;
import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Matiere;
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



    public boolean inscriptionStudent(Long id, Long idFiliere){
        Student student = studentList.findById(id)
                .orElseThrow(() -> new StudentNotFountException("Cet etudiant n'existe pas"));

        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new FiliereNotFound("Cette filiere n'existe pas"));

        if (studentList.existsByIdAndNotesIsNotEmpty(id)){
            throw new NoteNotFount("Impossible de changer de filiere car vous avez deja au moin une note enregistré");
        }

        student.setFiliere(filiere);

        studentList.save(student);

        return true;
    }
}
