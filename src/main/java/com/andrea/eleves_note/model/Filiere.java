package com.andrea.eleves_note.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Entity
@Data
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @OneToMany(mappedBy = "filiere")
    @JsonIgnore
    private Set<Student> students;

    @OneToMany(mappedBy = "filiere")
    private  Set<Matiere> matieres;
}
