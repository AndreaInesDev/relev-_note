package com.andrea.eleves_note.model;

import com.andrea.eleves_note.Appreciations;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valeur;

   // @Enumerated(EnumType.STRING)
    private Appreciations apprecications;

    @ManyToOne
    @JoinColumn(name = "id_matiere")
    @JsonIgnore
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "id_eleve")
    @JsonIgnore
    private Student student;
}
