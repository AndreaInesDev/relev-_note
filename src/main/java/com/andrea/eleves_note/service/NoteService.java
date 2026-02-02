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

    public Note saveNote(Note  note, Long idStudent, Long idMatiere){

        Student student = studentRepository.findById(idStudent)
                .orElseThrow(() -> new StudentNotFountException("Étudiant introuvable avec l'ID : " + idStudent));

        Matiere matiere = matiereRepository.findById(idMatiere)
                .orElseThrow(() -> new MatiereNotFound("Matière introuvable avec l'ID : " + idMatiere));

        double noteValeur = note.getValeur();

        if (noteValeur < 1 || noteValeur > 20){
            throw new RuntimeException("Impossible d'ajouter une valeur < 1 ou > 20");
        }else if (noteValeur < 10){
            note.setApprecications(Appreciations.FAIBLE);
        }else if (noteValeur < 12 ){
            note.setApprecications(Appreciations.PASSABLE);
        } else if (noteValeur < 14) {
            note.setApprecications(Appreciations.ASSEZ_BIEN);
        } else if (noteValeur < 16) {
            note.setApprecications(Appreciations.BIEN);
        } else if (noteValeur < 18) {
            note.setApprecications(Appreciations.TRES_BIEN);
        } else {
            note.setApprecications(Appreciations.PARFAIT);
        }


        // 1. Vérification de sécurité pour éviter le NullPointerException
        if (student.getFiliere() == null) {
            throw new RuntimeException("L'étudiant " + student.getName() + " n'est inscrit dans aucune filière.");
        }
//
//        //je verifie que la matiere appartient à une filiere
//        if (matiere.getFiliere() == null) {
//            throw new RuntimeException("La matière " + matiere.getLibelle() + " n'est rattachée à aucune filière.");
//        }

        //on verifie si les matieres appartiennent à la filieres de l'etudiant
        if (!matiere.getFiliere().getId().equals(student.getFiliere().getId())){
            throw new RuntimeException("cette matière n'est pas inscrite dans cette filière");
        }

        //on verifie si l'etudiant n'a pas deja de note sur cette matiere là
        if (noteRepository.existsByStudentAndMatiere(student, matiere)) {
            throw new RuntimeException("cette etudiant possède deja une note dans cette matiere");
        }


        note.setMatiere(matiere);
        note.setStudent(student);
        return noteRepository.save(note);
    }



    public String deleteNote(Long id){
        if (!noteRepository.findById(id).isPresent()){
            throw new NoteNotFount("Cette note n'existe pas");
        }

        noteRepository.deleteById(id);
        return "La note a été supprimée avec succès";
    }
}
