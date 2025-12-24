package com.andrea.eleves_note.service;

import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.model.Matiere;
import com.andrea.eleves_note.ripository.FiliereRepository;
import com.andrea.eleves_note.ripository.MatiereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatiereService {
    private final MatiereRepository matiereRepository;
    private  final FiliereRepository filiereRepository;

    public List<Matiere> getAllMatiere(){
        return matiereRepository.findAll();
    }

    public Matiere saveMatiere(Matiere matiere){
        if (matiere.getFiliere() == null || matiere.getFiliere().getId() == null){
            throw new RuntimeException("filiere manquante");
        }
       Long idFil = matiere.getFiliere().getId();
        Optional<Filiere> filiereOptional = filiereRepository.findById(idFil);
        if (!filiereOptional.isPresent()){
            throw new RuntimeException("Cette filiere n'existe pas");
        }
        matiere.setFiliere(filiereOptional.get());
        return matiereRepository.save(matiere);
    }

    public boolean deleteMatiere(Long id){
        Optional<Matiere> optionalMatiere = matiereRepository.findById(id);
        if (!optionalMatiere.isPresent()){
            throw new RuntimeException("Cette matiere n'existe pas");
        }
        matiereRepository.deleteById(id);
        return true;
    }
}
