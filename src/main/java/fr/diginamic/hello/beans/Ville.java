package fr.diginamic.hello.beans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Ville {

    @Min(value=1, message="L'id de la ville doit etre superieur ou égal a 1")
    private Integer id;

    @NotNull
    @Size(min=2, message="Le nom de la ville doit avoir au moins 2 caractères")
    private String nom;

    @Min(value=1, message="Le nombre d'habitants doit etre superieur ou egal a 1")
    private Integer nbHabitants;

    public Ville(String nom, Integer nbHabitants) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(Integer nbHabitants) {
        this.nbHabitants = nbHabitants;
    }
}
