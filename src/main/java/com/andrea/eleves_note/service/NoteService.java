package com.andrea.eleves_note.service;

import com.andrea.eleves_note.Appreciations;
import com.andrea.eleves_note.exception.MatiereNotFound;
import com.andrea.eleves_note.exception.NoteNotFount;
import com.andrea.eleves_note.exception.StudentNotFountException;
import com.andrea.eleves_note.model.Matiere;
import com.andrea.eleves_note.model.Note;
import com.andrea.eleves_note.model.Student;
import com.andrea.eleves_note.ripository.MatiereRepository;
import com.andrea.eleves_note.ripository.NoteRepository;
import com.andrea.eleves_note.ripository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final MatiereRepository matiereRepository;
    private final StudentRepository studentRepository;

    public List<Note> getAll(){
       return noteRepository.findAll();
    }

    public Note saveNote(Note  note){
        if (note.getMatiere() == null || note.getMatiere().getId() == null){
            throw new MatiereNotFound("Vous n'avez pas renseigné la matiere");
        }

        Optional<Matiere> matiereOptional =matiereRepository.findById(note.getMatiere().getId());
        if (!matiereOptional.isPresent()){
            throw new MatiereNotFound("Cette matiere n'existe pas");
        }

        if (note.getStudent() == null || note.getStudent().getId() == null){
            throw new StudentNotFountException("Vous n'avez pas renseigné l'etudiant");
        }

        Optional<Student> studentOptional = studentRepository.findById(note.getStudent().getId());
        if (!studentOptional.isPresent()){
            throw new StudentNotFountException("Cet etudiant n'existe pas");
        }
        double appreciation = note.getValeur();

        if (appreciation < 1 || appreciation > 20){
            throw new RuntimeException("Impossible d'ajouter une valeur < 1 ou > 20");
        }else if (appreciation < 10){
            note.setApprecications(Appreciations.FAIBLE);
        }else if (appreciation < 12 ){
            note.setApprecications(Appreciations.PASSABLE);
        } else if (appreciation < 14) {
            note.setApprecications(Appreciations.ASSEZ_BIEN);
        } else if (appreciation < 16) {
            note.setApprecications(Appreciations.BIEN);
        } else if (appreciation < 18) {
            note.setApprecications(Appreciations.TRES_BIEN);
        } else {
            note.setApprecications(Appreciations.PARFAIT);
        }

        Matiere matiere = matiereOptional.get();
        Student student = studentOptional.get();

        if (noteRepository.existsByStudentAndMatiere(student, matiere)) {
            throw new RuntimeException("cette etudiant possede deja une note dans cette matiere");
        }

        // 1. Vérification de sécurité pour éviter le NullPointerException
        if (student.getFiliere() == null) {
            throw new RuntimeException("L'étudiant " + student.getName() + " n'est inscrit dans aucune filière.");
        }
        if (matiere.getFiliere() == null) {
            throw new RuntimeException("La matière " + matiere.getLibelle() + " n'est rattachée à aucune filière.");
        }


        //on verifie si les matieres appartiennent à la filieres de l'etudiant
        if (!matiere.getFiliere().getId().equals(student.getFiliere().getId())){
            throw new RuntimeException("L'étudiant et la matière ne sont pas dans la même filière.");
        }

        //on verifie si l'etudiant n'a pas deja de note sur cette matiere là


        note.setMatiere(matiere);
        note.setStudent(student);
        return noteRepository.save(note);
    }

    public List<Note> saveAllNote(List<Note> notes){
        List<Note> notes1 = new ArrayList<>();
        for (Note note : notes){
            notes1.add(this.saveNote(note));
        }

        return notes1;
    }

    public String deleteNote(Long id){
        if (!noteRepository.findById(id).isPresent()){
            throw new NoteNotFount("Cette note n'existe pas");
        }

        noteRepository.deleteById(id);
        return "La note a été supprimée avec succès";
    }
}
