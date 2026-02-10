package com.andrea.eleves_note.service;

import com.andrea.eleves_note.Appreciations;
import com.andrea.eleves_note.dto.Moyennedto;
import com.andrea.eleves_note.exception.FiliereNotFound;
import com.andrea.eleves_note.exception.MatiereNotFound;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final MatiereRepository matiereRepository;
    private final StudentRepository studentRepository;
    private  final FiliereRepository filiereRepository;

    public List<Note> getAll(){
       return noteRepository.findAll();
    }

    public Note saveNote(Note  note, Long idStudent, Long idMatiere){

        Student student = studentRepository.findById(idStudent)
                .orElseThrow(() -> new StudentNotFountException("Étudiant introuvable avec l'ID : " + idStudent));

        Matiere matiere = matiereRepository.findById(idMatiere)
                .orElseThrow(() -> new MatiereNotFound("Matière introuvable avec l'ID : " + idMatiere));

        double noteValeur = note.getValeur();

        note.setApprecications(Appreciations.getFromNote(noteValeur));


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

    public Double moyenneEtudiant(Long id){
        Student student = studentRepository.findById(id)
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

    public List<Moyennedto> classementGeneral() {
        // 1. Récupérer tous les étudiants de la base de données
        List<Student> allStudents = studentRepository.findAll();

        // 2. Créer une liste pour stocker nos résultats (Nom + Moyenne)
        List<Moyennedto> classement = new ArrayList<>();

        // 3. Boucler sur chaque étudiant pour calculer sa moyenne
        for (Student student : allStudents) {
            Double moyenne = moyenneEtudiant(student.getId());

            // On ajoute le résultat dans notre liste de DTO
            classement.add(new Moyennedto(student.getName(), moyenne));
        }

        // 4. Trier la liste par moyenne décroissante (du plus grand au plus petit)
        // m2.getMoyenne().compareTo(m1.getMoyenne()) inverse l'ordre naturel (décroissant)
        classement.sort((m1, m2) -> m2.moyenne().compareTo(m1.moyenne()));

        return classement;
    }

    public Double moyenneClasse(Long idFiliere){
        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new FiliereNotFound("Cette filiere n'existe pas"));

        List<Student> studentList = studentRepository.findByFiliereId(idFiliere);

        if (studentList.isEmpty()){
            return 0.0;
        }

        Double moyenneGenerale = 0.0;
        int etudiantAvecNotes = 0;

        for (Student student : studentList){

            List<Note> list = noteRepository.findByStudentId(student.getId());

            if (!list.isEmpty()){
                Double moyenneTotal = moyenneEtudiant(student.getId());
                moyenneGenerale += moyenneTotal;
                etudiantAvecNotes++;

            }
        }

        return   (etudiantAvecNotes == 0) ? 0.0 : moyenneGenerale / etudiantAvecNotes ;
    }
}
