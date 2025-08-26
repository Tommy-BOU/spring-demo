package fr.diginamic.spring.demo.controleurs;

import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import fr.diginamic.spring.demo.services.VilleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
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
    public List<VilleDto> getVilles(@RequestParam int page, @RequestParam int size) {
        PageRequest pagination = PageRequest.of(page, size);
        return service.extractVilles(pagination);
    }

    /**
     * Retourne une ville selon son nom
     *
     * @param nom Le nom de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/name/{nom}/all")
    public List<VilleDto> getAllVilleByName(@PathVariable String nom) throws ExceptionRequeteInvalide {
        return service.extractVillesByNom(nom);
    }

    /**
     * Retourne la liste des villes dont la population est supÃrieure Ã  {@code min}
     * @param min La population minimale
     * @return La liste des {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/pop/{min}")
    public List<VilleDto> getAllVillesPopGreaterThan(@PathVariable int min) throws ExceptionRequeteInvalide {
        return service.extractVillesByNbHabitantsGreaterThan(min);
    }


    /**
     * Retourne la liste des villes dont la population est comprise entre {@code min} et {@code max}
     * @param min La population minimale
     * @param max La population maximale
     * @return La liste des {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/pop/{min}/{max}")
    public List<VilleDto> getAllVillesPopBetween(@PathVariable int min, @PathVariable int max) throws ExceptionRequeteInvalide {
        return service.extractVillesByNbHabitantsBetween(min, max);
    }

    /**
     * Retourne une ville selon son id
     *
     * @param id L'identifiant de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/{id}")
    public VilleDto getVilleById(@PathVariable int id) throws ExceptionRequeteInvalide {
        return service.extractVille(id);
    }

    /**
     * Retourne une ville selon son nom
     *
     * @param nom Le nom de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/name/{nom}")
    public VilleDto getVilleByName(@PathVariable String nom) throws ExceptionRequeteInvalide {
        return service.extractVille(nom);
    }


    /**
     * Ajoute une ville dans la liste
     *
     * @param newVille La ville ajoutée
     * @return Liste des {@link Ville} après insertion
     */
    @PostMapping
    public List<VilleDto> ajouterVille(@RequestBody Ville newVille) throws ExceptionRequeteInvalide {
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
    public List<VilleDto> modifierVille(@PathVariable int id, @RequestBody Ville data) throws ExceptionRequeteInvalide {
        return service.modifierVille(id, data);
    }

    /**
     * Supprime une {@link Ville} de la liste par son identifiant.
     *
     * @param id L'identifiant de la {@link Ville} à supprimer
     * @return La liste des {@link Ville} apres suppression
     */
    @DeleteMapping(path = "/{id}")
    public List<VilleDto> supprimerVille(@PathVariable int id) throws ExceptionRequeteInvalide {
        return service.supprimerVille(id);
    }

}
