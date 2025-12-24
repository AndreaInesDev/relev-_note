package com.andrea.eleves_note.ripository;

import com.andrea.eleves_note.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    boolean existsByLibelle(String libelle);
}
