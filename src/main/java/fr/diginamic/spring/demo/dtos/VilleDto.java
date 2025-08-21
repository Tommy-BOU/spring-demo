package fr.diginamic.spring.demo.dtos;

import fr.diginamic.spring.demo.beans.Ville;

public class VilleDto {
    private Integer id;
    private String nom;
    private Integer nbHabitants;
    private String codePostal;
    private String nomDepartement;
    private String codeDepartement;

    public VilleDto() {
    }

    public VilleDto(Ville ville) {
        this.id = ville.getId();
        this.nom = ville.getNom();
        this.nbHabitants = ville.getNbHabitants();
        this.codePostal = ville.getCodePostal();
        this.codeDepartement = ville.getDepartement().getCodeDepartement();
        this.nomDepartement = ville.getDepartement().getNom();
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

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }
}
