package com.andrea.eleves_note.service;

import com.andrea.eleves_note.exception.ExistFiliere;
import com.andrea.eleves_note.exception.FiliereNotFound;
import com.andrea.eleves_note.model.Filiere;
import com.andrea.eleves_note.ripository.FiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FiliereService {
    private final FiliereRepository filiereRepository;

    public List<Filiere> getAllFiliere(){
        return filiereRepository.findAll();
    }

    public  Filiere saveFiliere(Filiere filiere){
        if (filiereRepository.existsByLibelle(filiere.getLibelle())){
            throw  new ExistFiliere("Cette filiere existe deja");
        }
        return filiereRepository.save(filiere);
    }

    public boolean deleteFiliere(Long id){
        Optional<Filiere> filiereOptional = filiereRepository.findById(id);
        if (!filiereOptional.isPresent()){
            throw  new FiliereNotFound("La filiere d'id" + id + " n'existe pas");
        }
        filiereRepository.deleteById(id);
        return true;
    }

    public Filiere getFiliere(Long id){
        Optional<Filiere> filiereOptional = filiereRepository.findById(id);
        if (filiereOptional.isPresent()){
            return filiereOptional.get();
        }

        throw new FiliereNotFound("Cette filiere n'existe pas");
    }

    public Filiere updateFiliere(Long id, Filiere filiere1){
        Filiere filiereExist = filiereRepository.findById(id)
                .orElseThrow(() ->  new FiliereNotFound("Cette filiere n'existe pas"));

        filiereExist.setLibelle(filiere1.getLibelle());

        return filiereRepository.save(filiereExist);
    }
}