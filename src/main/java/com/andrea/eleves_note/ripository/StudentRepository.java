package com.andrea.eleves_note.ripository;

import com.andrea.eleves_note.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByMatricule(String matricule);
    Student findByMatricule(String matricule);
}
