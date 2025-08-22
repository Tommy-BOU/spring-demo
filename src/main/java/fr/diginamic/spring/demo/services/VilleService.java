package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.daos.DepartementDao;
import fr.diginamic.spring.demo.daos.VilleDao;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.dtos.VilleMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de service pour gérer les {@link Ville}.
 */
@Service
public class VilleService {

    @Autowired
    private VilleDao dao;

    @Autowired
    private DepartementDao daoDepartement;

    @Autowired
    private VilleMapper mapper;

    /**
     * Récupère toutes les villes.
     *
     * @return Liste de {@link Ville}.
     */
    public List<VilleDto> extractVilles() {
        return mapper.toDtos(dao.findAll());
    }

    /**
     * Récupère une {@link Ville} par son ID.
     *
     * @param id ID de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public ResponseEntity<?> extractVille(int id) {
        Ville ville = dao.findById(id);
        if (ville == null) {
            return ResponseEntity.badRequest().body("Aucune ville ne correspond à l'ID");
        }
        return ResponseEntity.ok().body(mapper.toDto(ville));
    }

    /**
     * Récupère une {@link Ville} par son nom.
     *
     * @param nom Nom de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public ResponseEntity<?> extractVille(String nom) {
        Ville ville = dao.findByNom(nom);
        if (ville == null) {
            return ResponseEntity.badRequest().body("Aucune ville ne correspond au nom");
        }
        return ResponseEntity.ok().body(mapper.toDto(ville));
    }

    /**
     * Ajoute une {@link Ville}.
     *
     * @param ville {@link Ville} à ajouter.
     * @return Liste des {@link Ville} après ajout ou un message d'erreur.
     */
    public ResponseEntity<?> insertVille(@RequestBody Ville ville) {
        if (valuesAreValid(ville)) {
//            Ajout du departement selon le code postal de la ville
            Departement departement = findDepartementFromCode(ville);
            if (departement == null) {
                return ResponseEntity.badRequest().body("Aucun département ne correspond au code postal de la ville");
            }
            ville.setDepartement(departement);
            dao.insertVille(ville);
            return ResponseEntity.ok().body(mapper.toDtos(dao.findAll()));
        }
        return ResponseEntity.badRequest().body("La ville n'a pas pu étre ajoutée (valeurs invalides)");
    }

    /**
     * Modifie une ville.
     *
     * @param id   ID de la ville.
     * @param data Instance de{@link Ville} avec les nouvelles données.
     * @return Liste des {@link Ville} après modification ou un message d'erreur.
     */
    @Transactional
    public ResponseEntity<?> modifierVille(int id, @RequestBody Ville data) {
        if (data.getId() == null) {
            data.setId(id);
        }
        if (valuesAreValid(data)) {
//            Modification du departement selon le code postal de la ville
            Departement departement = findDepartementFromCode(data);
            if (departement == null) {
                return ResponseEntity.badRequest().body("Aucun département ne correspond au code postal de la ville");
            }
            data.setDepartement(departement);
            dao.modifierVille(id, data);
            return ResponseEntity.ok().body(mapper.toDtos(dao.findAll()));
        }
        return ResponseEntity.badRequest().body("La ville n'a pas pu étre modifiée (valeurs invalides)");
    }

    /**
     * Supprime une ville.
     *
     * @param id ID de la ville.
     * @return Liste des {@link Ville} après suppression.
     */
    public ResponseEntity<?> supprimerVille(int id) {
        Ville ville = dao.findById(id);
        if (ville == null) {
            return ResponseEntity.badRequest().body("Aucune ville ne correspond à l'ID");
        }
        dao.supprimerVille(id);
        return ResponseEntity.ok().body(mapper.toDtos(dao.findAll()));
    }

    /**
     * Vérifie les valeurs de la {@link Ville}
     *
     * @param ville La {@link Ville} à verifier
     * @return true si les valeurs sont valides, false sinon
     */
    public boolean valuesAreValid(Ville ville) {
//        Si la ville a un ID, vérifie qu'il est >= 0 en plus des autres valeurs (mise à jour)
        if (ville.getId() != null) {
            return ville.getId() >= 0 && ville.getNom() != null && ville.getNom().length() >= 2 && ville.getNbHabitants() >= 1 && ville.getCodePostal() != null;
        }
//        Sinon vérifie uniquement les autres valeurs (création)
        return ville.getNom() != null && ville.getNom().length() >= 2 && ville.getNbHabitants() >= 1 && ville.getCodePostal() != null;
    }

    /**
     * Récupère le {@link Departement} correspondant au code postal de la {@link Ville}
     *
     * @param ville La {@link Ville} dont on veut le {@link Departement}
     * @return Le {@link Departement} correspondant. Null si aucun {@link Departement} ne correspond au code postal de la {@link Ville}
     */
    public Departement findDepartementFromCode(Ville ville) {
        String codeDepartement = ville.getCodePostal().substring(0, 2);
        List<Departement> departements = daoDepartement.findAll();
        for (Departement departement : departements) {
            if (departement.getCodeDepartement().equals(codeDepartement)) {
                ville.setDepartement(departement);
                return departement;
            }
        }
        return null;
    }
}