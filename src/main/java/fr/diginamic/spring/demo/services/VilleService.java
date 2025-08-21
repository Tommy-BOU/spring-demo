package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.daos.DepartementDao;
import fr.diginamic.spring.demo.daos.VilleDao;
import fr.diginamic.spring.demo.dtos.VilleDto;
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

    /**
     * Récupère toutes les villes.
     *
     * @return Liste de {@link Ville}.
     */
    public List<VilleDto> extractVilles() {
        List<Ville> villes = dao.findAll();
        List<VilleDto> villesDto = new ArrayList<>();
        for (Ville ville : villes) {
            villesDto.add(new VilleDto(ville));
        }
        return villesDto;
    }

    /**
     * Récupère une {@link Ville} par son ID.
     *
     * @param id ID de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public VilleDto extractVille(int id) {
        Ville ville = dao.findById(id);
        return new VilleDto(ville);
    }

    /**
     * Récupère une {@link Ville} par son nom.
     *
     * @param nom Nom de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public VilleDto extractVille(String nom) {
        Ville ville = dao.findByNom(nom);
        return new VilleDto(ville);
    }

    /**
     * Ajoute une {@link Ville}.
     *
     * @param ville {@link Ville} à ajouter.
     * @return Liste des {@link Ville} après ajout ou un message d'erreur.
     */
    @PostMapping
    public ResponseEntity<?> insertVille(@RequestBody Ville ville) {
        if (valuesAreValid(ville)) {
            String codeDepartement = ville.getCodePostal().substring(0, 2);
            ville.setDepartement(daoDepartement.findByCodeDepartement(codeDepartement));
            List<Ville> villes = dao.insertVille(ville);
            List<VilleDto> villesDto = new ArrayList<>();
            for (Ville v : villes) {
                villesDto.add(new VilleDto(v));
            }
            return ResponseEntity.ok().body(villesDto);
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
    @PutMapping
    public ResponseEntity<?> modifierVille(int id, @RequestBody Ville data) {
        if (data.getId() == null) {
            data.setId(id);
        }
        if (valuesAreValid(data)) {
            String codeDepartement = data.getCodePostal().substring(0, 2);
            data.setDepartement(daoDepartement.findByCodeDepartement(codeDepartement));
            List<Ville> villes = dao.modifierVille(id, data);
            List<VilleDto> villesDto = new ArrayList<>();
            for (Ville v : villes) {
                villesDto.add(new VilleDto(v));
            }
            return ResponseEntity.ok().body(villesDto);
        }
        return ResponseEntity.badRequest().body("La ville n'a pas pu étre modifiée (valeurs invalides)");
    }

    /**
     * Supprime une ville.
     *
     * @param id ID de la ville.
     * @return Liste des {@link Ville} après suppression.
     */
    @DeleteMapping
    public List<VilleDto> supprimerVille(int id) {
        List<Ville> villes = dao.supprimerVille(id);
        List<VilleDto> villesDto = new ArrayList<>();
        for (Ville ville : villes) {
            villesDto.add(new VilleDto(ville));
        }
        return villesDto;
    }

    /**
     * Vérifie les valeurs de la {@link Ville}
     *
     * @param ville La {@link Ville} à verifier
     * @return true si les valeurs sont valides, false sinon
     */
    public boolean valuesAreValid(Ville ville) {
        if (ville.getId() != null) {
            return ville.getId() >= 0 && ville.getNom() != null && ville.getNom().length() >= 2 && ville.getNbHabitants() >= 1 && ville.getCodePostal() != null;
        }
        return ville.getNom() != null && ville.getNom().length() >= 2 && ville.getNbHabitants() >= 1 && ville.getCodePostal() != null;
    }
}