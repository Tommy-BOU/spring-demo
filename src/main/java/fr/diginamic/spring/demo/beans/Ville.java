package fr.diginamic.spring.demo.beans;

import fr.diginamic.spring.demo.validationGroups.ModeCreation;
import fr.diginamic.spring.demo.validationGroups.ModeModification;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ville {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Min(value=1, message="L'id de la ville doit etre superieur ou égal a 1", groups=ModeModification.class)
    private Integer id;

    @NotNull
    @Size(min=2, message="Le nom de la ville doit avoir au moins 2 caractères", groups={ModeModification.class, ModeCreation.class})
    private String nom;

    @Column(name="nb_habitants")
    @Min(value=1, message="Le nombre d'habitants doit etre superieur ou egal a 1", groups={ModeModification.class, ModeCreation.class})
    private Integer nbHabitants;

    @NotNull
    @Column(name="code_postal")
    private String codePostal;

    @ManyToOne(cascade = CascadeType.ALL)
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
}
