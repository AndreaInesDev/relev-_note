package com.andrea.eleves_note.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "students")

@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private  String username;
    private String address;
    private String matricule;

    @ManyToOne
    @JoinColumn(name = "id_filiere")
    private Filiere filiere ;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Note> notes;

}
