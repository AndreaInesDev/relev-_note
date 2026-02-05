package com.andrea.eleves_note.service;

import com.andrea.eleves_note.exception.FiliereNotFound;
import com.andrea.eleves_note.exception.NoteNotFount;
import com.andrea.eleves_note.exception.StudentNotFountException;
import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Student;
import com.andrea.eleves_note.ripository.FiliereRepository;
import com.andrea.eleves_note.ripository.MatiereRepository;
import com.andrea.eleves_note.ripository.NoteRepository;
import com.andrea.eleves_note.ripository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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

        student.setMatricule(genererMatricule());

        while (studentList.existsByMatricule(student.getMatricule())){
            student.setMatricule(genererMatricule());
        }

        return studentList.save(student);
    }

    private String genererMatricule() {
        //LocalDate.now donne la date d'aujourdhui et le getYear recupere uniquement l'annee
        int annee = LocalDate.now().getYear();

        char lettre = (char) ('A' + new Random().nextInt(26));

        int nombre = 10 + new Random().nextInt(90);
        System.out.println(annee);
        return String.format("%d-%c%d", annee, lettre, nombre);
    }

    public void deleteStudent(Long id) {
        Student student = studentList.findById(id)
                .orElseThrow(() -> new StudentNotFountException("Cet etudiant n'existe pas"));

        studentList.delete(student);
    }

    public Student updateStudent(Long id, Student student) {
        Student student1 = studentList.findById(id)
                .orElseThrow(() -> new StudentNotFountException("Cet etudiant n'existe pas"));
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

    public List<Student> etudiantAyantNote(){
        List<Student> students = studentList.findByNotesIsNotEmpty();

        return students;
    }
}
