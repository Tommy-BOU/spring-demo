package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.daos.VilleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Classe de service pour gérer les {@link Ville}.
 */
@Service
public class VilleService {

    @Autowired
    private VilleDao dao;

    /**
     * Récupère toutes les villes.
     * @return Liste de {@link Ville}.
     */
    public List<Ville> extractVilles() {
        return dao.findAll();
    }

    /**
     * Récupère une {@link Ville} par son ID.
     * @param id ID de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public Ville extractVille(int id) {
        return dao.findById(id);
    }

    /**
     * Récupère une {@link Ville} par son nom.
     * @param nom Nom de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public Ville extractVille(String nom) {
        return dao.findByNom(nom);
    }

    /**
     * Ajoute une {@link Ville}.
     * @param ville {@link Ville} à ajouter.
     * @return Liste des {@link Ville} après ajout.
     */
    @PostMapping
    public List<Ville> insertVille(@RequestBody Ville ville) {
        return dao.insertVille(ville);
    }

    /**
     * Modifie une ville.
     * @param id ID de la ville.
     * @param ville {@link Ville} avec les nouvelles données.
     * @return Liste des {@link Ville} après modification.
     */
    @PutMapping
    public List<Ville> modifierVille(int id, @RequestBody Ville ville) {
        return dao.modifierVille(id, ville);
    }

    /**
     * Supprime une ville.
     * @param id ID de la ville.
     * @return Liste des {@link Ville} après suppression.
     */
    @DeleteMapping
    public List<Ville> supprimerVille(int id) {
        return dao.supprimerVille(id);
    }
}