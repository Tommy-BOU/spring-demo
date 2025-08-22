package fr.diginamic.spring.demo.dtos;

import fr.diginamic.spring.demo.beans.Ville;

public class VilleDto {
    private Integer id;
    private String nom;
    private Integer nbHabitants;
    private String codePostal;
    private DepartementDto departement;

    public VilleDto() {
    }

    public VilleDto(Ville ville, boolean withDepartement) {
        this.id = ville.getId();
        this.nom = ville.getNom();
        this.nbHabitants = ville.getNbHabitants();
        this.codePostal = ville.getCodePostal();
        if (withDepartement) {
            this.departement = new DepartementDto(ville.getDepartement(), false);
        }
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

    public DepartementDto getDepartement() {
        return departement;
    }

    public void setDepartement(DepartementDto departement) {
        this.departement = departement;
    }
}
