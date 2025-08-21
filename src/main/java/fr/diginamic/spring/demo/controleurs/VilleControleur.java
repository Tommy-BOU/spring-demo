package fr.diginamic.spring.demo.controleurs;

import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.services.VilleService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Controleur des {@link Ville}
 */
@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private Validator validator;

    @Autowired
    private VilleService service;

    /**
     * Retourne la liste des {@link Ville}
     *
     * @return List<Ville>
     */
    @GetMapping
    public List<VilleDto> getVilles() {
        return service.extractVilles();
    }

    /**
     * Retourne une ville selon son id
     *
     * @param id L'identifiant de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/{id}")
    public VilleDto getVilleById(@PathVariable int id) {
        return service.extractVille(id);
    }

    /**
     * Retourne une ville selon son nom
     *
     * @param nom Le nom de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/{nom}")
    public VilleDto getVilleByName(@PathVariable String nom) {
        return service.extractVille(nom);
    }

    /**
     * Ajoute une ville dans la liste
     *
     * @param newVille La ville ajoutée
     * @return Liste des {@link Ville} après insertion
     */
    @PostMapping
    public ResponseEntity<?> ajouterVille(@RequestBody Ville newVille) {
        return service.insertVille(newVille);
    }

    /**
     * Modifie une {@link Ville} de la liste par son identifiant
     *
     * @param id   L'identifiant de la {@link Ville} à modifier
     * @param data Les nouvelles données de la {@link Ville} sous forme d'instance
     * @return La liste des {@link Ville} apres modification
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> modifierVille(@PathVariable int id, @RequestBody Ville data) {
        return service.modifierVille(id, data);
    }

    /**
     * Supprime une {@link Ville} de la liste par son identifiant.
     *
     * @param id L'identifiant de la {@link Ville} à supprimer
     * @return La liste des {@link Ville} apres suppression
     */
    @DeleteMapping(path = "/{id}")
    public List<VilleDto> supprimerVille(@PathVariable int id) {
        return service.supprimerVille(id);
    }

}
