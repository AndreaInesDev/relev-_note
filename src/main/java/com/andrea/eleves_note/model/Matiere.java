package com.andrea.eleves_note.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Entity
@Data
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @ManyToOne
    @JoinColumn(name = "id_filiere")
    @JsonIgnore
    private Filiere filiere;

    @OneToMany(mappedBy = "matiere" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Note> notes;
}
