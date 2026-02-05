package com.andrea.eleves_note.ripository;

import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    boolean existsByLibelle(String libelle);
}
