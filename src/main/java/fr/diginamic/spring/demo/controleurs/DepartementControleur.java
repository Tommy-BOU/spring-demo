package fr.diginamic.spring.demo.controleurs;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controleur des {@link Departement}
 */
@RestController
@RequestMapping("/departements")
public class DepartementControleur {
    @Autowired
    private Validator validator;

    @Autowired
    private DepartementService service;

    /**
     * Retourne la liste des {@link Departement}
     *
     * @return List<Departement>
     */
    @GetMapping
    public List<DepartementDto> getDepartements() {
        return service.extractDepartements();
    }

    /**
     * Retourne un departement selon son id
     *
     * @param id L'identifiant du departement
     * @return Le {@link Departement} ou un message d'erreur
     */
    @GetMapping(path = "/{id}")
    public DepartementDto getDepartementById(@PathVariable int id) {
        return service.extractDepartement(id);
    }

    /**
     * Retourne un departement selon son nom
     *
     * @param code Le code de la departement
     * @return La {@link Departement} ou un message d'erreur
     */
    @GetMapping(path = "/{code}")
    public DepartementDto getDepartementByCode(@PathVariable String code) {
        return service.extractDepartement(code);
    }

    /**
     * Ajoute une departement dans la liste
     *
     * @param newDepartement La departement ajoutée
     * @return Liste des {@link Departement} après insertion
     */
    @PostMapping
    public ResponseEntity<?> ajouterDepartement(@RequestBody Departement newDepartement) {
        return service.insertDepartement(newDepartement);
    }

    /**
     * Modifie une {@link Departement} de la liste par son identifiant
     *
     * @param id   L'identifiant de la {@link Departement} à modifier
     * @param data Les nouvelles données de la {@link Departement} sous forme d'instance
     * @return La liste des {@link Departement} apres modification
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> modifierDepartement(@PathVariable int id, @RequestBody Departement data) {

        return service.modifierDepartement(id, data);

    }

    /**
     * Supprime une {@link Departement} de la liste par son identifiant.
     *
     * @param id L'identifiant de la {@link Departement} à supprimer
     * @return La liste des {@link Departement} apres suppression
     */
    @DeleteMapping(path = "/{id}")
    public List<DepartementDto> supprimerDepartement(@PathVariable int id) {
        return service.supprimerDepartement(id);
    }
}
