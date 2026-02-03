package com.andrea.eleves_note.ripository;

import com.andrea.eleves_note.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByMatricule(String matricule);
    Student findByMatricule(String matricule);

    boolean existsByIdAndNotesIsNotEmpty(Long id);

    List<Student> findByFiliereId(Long idFiliere);
}
