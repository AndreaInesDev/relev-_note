package com.andrea.eleves_note.service;

import com.andrea.eleves_note.exception.FiliereNotFound;
import com.andrea.eleves_note.exception.MatiereNotFound;
import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Matiere;
import com.andrea.eleves_note.ripository.FiliereRepository;
import com.andrea.eleves_note.ripository.MatiereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatiereService {
    private final MatiereRepository matiereRepository;
    private final FiliereRepository filiereRepository;
    private static final String MATIERE_NOT_FOUND_MSG = "Cette matiere n'existe pas";

    public List<Matiere> getAllMatiere(){
        return matiereRepository.findAll();
    }


    public Matiere getMatiereById(Long id){

            return matiereRepository.findById(id)
                .orElseThrow(() -> new MatiereNotFound(MATIERE_NOT_FOUND_MSG));

    }

    public Matiere saveMatiere(Matiere matiere){

        Matiere matiere1 = matiereRepository.findByLibelleIgnoreCase(matiere.getLibelle());
         if (matiere1 != null && !matiere.getId().equals(matiere1.getId())){
            throw new  MatiereNotFound(MATIERE_NOT_FOUND_MSG);
        }

        return matiereRepository.save(matiere);
    }

    public List<Matiere> saveAllMatiere(List<Matiere> matieres){
        List<Matiere> matiereSaved= new ArrayList<>();

        for (Matiere matiere : matieres){
            matiereSaved.add(this.saveMatiere(matiere));
        }

        return matiereSaved;
    }

    public boolean deleteMatiere(Long id){
        Optional<Matiere> optionalMatiere = matiereRepository.findById(id);
        if (!optionalMatiere.isPresent()){
            throw new MatiereNotFound(MATIERE_NOT_FOUND_MSG);
        }
        matiereRepository.deleteById(id);
        return true;
    }

    public  Matiere updateMatiere(Long id, Matiere matiere){
        Matiere matiereUpdate = matiereRepository.findById(id)
                .orElseThrow(() -> new MatiereNotFound(MATIERE_NOT_FOUND_MSG));

        Long filiereId = matiereUpdate.getFiliere().getId();

        if (!matiereUpdate.getLibelle().equalsIgnoreCase(matiere.getLibelle())){
            boolean matiereFiliere = matiereRepository.existsByLibelleIgnoreCaseAndFiliereId(matiere.getLibelle(),
                    filiereId);

            if (matiereFiliere){
                throw new MatiereNotFound("La matiere " + matiere.getLibelle() +
                        " existe déjà pour la filière " + matiereUpdate.getFiliere().getLibelle());
            }
        }

        matiereUpdate.setLibelle(matiere.getLibelle());

        return matiereRepository.save(matiereUpdate);
    }

    public Matiere inscriptionMatiereFiliere(Long idMatiere, Long idFiliere){
        Matiere matiere = matiereRepository.findById(idMatiere)
                .orElseThrow(() -> new MatiereNotFound(MATIERE_NOT_FOUND_MSG));

        Filiere filiere = filiereRepository.findById(idFiliere)
                .orElseThrow(() -> new FiliereNotFound("Cette filiere n'existe pas"));

        matiere.setFiliere(filiere);
        return matiereRepository.save(matiere) ;
    }

    public List<Matiere> listeMatiere(Long idFiliere){

        if (!filiereRepository.existsById(idFiliere)) {
            throw new FiliereNotFound("Cette filiere n'existe pas");
        }
       return matiereRepository.findByFiliereId(idFiliere);
    }

}


