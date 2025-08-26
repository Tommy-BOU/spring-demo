package fr.diginamic.spring.demo.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class Ville {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Size(min=2, message="Le nom de la ville doit avoir au moins 2 caractères")
    private String nom;

    @Column(name="nb_habitants")
    @Min(value=1, message="Le nombre d'habitants doit etre superieur ou égal a 1")
    private Integer nbHabitants;

    @Size(min=5, max=5, message="Le code postal doit avoir 5 chiffres")
    @Column(name="code_postal")
    private String codePostal;

    @ManyToOne
    @Null
    @JoinColumn(name="id_departement")
    private Departement departement;

    public Ville() {
    }

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

    public @NotNull Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        if (this.departement != null) {
            this.departement.removeVille(this);
        }
        this.departement = departement;
        if (this.departement != null) {
            this.departement.getVilles().add(this);
        }
    }

    public @NotNull String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(@NotNull String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ville ville)) return false;
        return Objects.equals(nom, ville.nom) && Objects.equals(codePostal, ville.codePostal) && Objects.equals(departement, ville.departement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, codePostal, departement);
    }
}
