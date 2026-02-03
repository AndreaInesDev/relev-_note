package com.andrea.eleves_note.ripository;

import com.andrea.eleves_note.model.Matiere;
import com.andrea.eleves_note.model.Note;
import com.andrea.eleves_note.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note , Long> {
    boolean existsByStudentAndMatiere(Student student, Matiere matiere);

    List<Note> findByStudentId(Long id);

    List<Note> findByMatiereId(Long idMatiere);
}
