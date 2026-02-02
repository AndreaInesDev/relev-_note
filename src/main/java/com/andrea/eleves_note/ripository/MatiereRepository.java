package com.andrea.eleves_note.ripository;

import com.andrea.eleves_note.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    boolean existsByLibelleIgnoreCase(String libelle);
    boolean existsByLibelleIgnoreCaseAndFiliereId(String libelle, Long filiereId);
    Matiere findByLibelleIgnoreCase(String libelle);
}
