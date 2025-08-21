package fr.diginamic.spring.demo.dtos;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;

import java.util.ArrayList;
import java.util.List;

public class DepartementDto {
    private int id;
    private String nom;
    private String codeDepartement;
    private List<VilleDto> villes;

    public DepartementDto() {

    }

    public DepartementDto(Departement departement) {
        this.id = departement.getId();
        this.nom = departement.getNom();
        this.codeDepartement = departement.getCodeDepartement();
        this.villes = new ArrayList<>();
        if (departement.getVilles() != null) {
            for (Ville ville : departement.getVilles()) {
                this.villes.add(new VilleDto(ville));
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<VilleDto> getVilles() {
        return villes;
    }

    public void setVilles(List<VilleDto> villes) {
        this.villes = villes;
    }
}
