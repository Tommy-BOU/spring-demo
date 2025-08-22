package fr.diginamic.spring.demo.beans;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @Column(name = "code_departement")
    private String codeDepartement;

    @OneToMany(mappedBy = "departement")
    private List<Ville> villes;

    public Departement() {
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

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    public void addVille(Ville ville) {
        if (ville != null) {
            ville.setDepartement(this);
        }
    }

    public void removeVille(Ville ville) {
        this.villes.remove(ville);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departement that)) return false;
        return Objects.equals(codeDepartement, that.codeDepartement);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codeDepartement);
    }
}
