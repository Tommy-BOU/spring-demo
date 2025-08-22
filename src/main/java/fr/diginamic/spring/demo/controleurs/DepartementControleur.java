package fr.diginamic.spring.demo.controleurs;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.VilleDto;
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
     * @param code Le code du departement
     * @return Le {@link Departement} ou un message d'erreur
     */
    @GetMapping(path = "/{code}")
    public DepartementDto getDepartementByCode(@PathVariable String code) {
        return service.extractDepartement(code);
    }

    /**
     * Ajoute un departement dans la liste
     *
     * @param newDepartement Le departement ajoutée
     * @return Liste des {@link Departement} après insertion
     */
    @PostMapping
    public ResponseEntity<?> ajouterDepartement(@RequestBody Departement newDepartement) {
        return service.insertDepartement(newDepartement);
    }

    /**
     * Modifie une {@link Departement} de la liste par son identifiant
     *
     * @param id   L'identifiant du {@link Departement} à modifier
     * @param data Les nouvelles données du {@link Departement} sous forme d'instance
     * @return La liste des {@link Departement} apres modification
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> modifierDepartement(@PathVariable int id, @RequestBody Departement data) {

        return service.modifierDepartement(id, data);

    }

    /**
     * Supprime un {@link Departement} de la liste par son identifiant.
     *
     * @param id L'identifiant de la {@link Departement} à supprimer
     * @return La liste des {@link Departement} apres suppression
     */
    @DeleteMapping(path = "/{id}")
    public List<DepartementDto> supprimerDepartement(@PathVariable int id) {
        return service.supprimerDepartement(id);
    }

    /**
     * Retourne la liste des villes d'un departement contenant {@code num} villes
     * @param id L'id du {@link Departement}
     * @param num Le nombre de villes à retourner
     * @return List<VilleDto>
     */
    @GetMapping(path = "/{id}/villes/{num}")
    public List<VilleDto> getVillesByDepartement(@PathVariable int id, @PathVariable int num) {
        return service.extractVillesByDepartement(id, num);
    }

    /**
     * Retourne la liste des villes d'un departement dont la population est comprise entre {@code popMin} et {@code popMax}
     * @param id L'id du {@link Departement}
     * @param popMin La population minimale
     * @param popMax La population maximale
     * @return une List<VilleDto>
     */
    @GetMapping(path = "/{id}/villes/{popMin}/{popMax}")
    public List<VilleDto> getVillesByDepartementAndPop(@PathVariable int id, @PathVariable int popMin, @PathVariable int popMax) {
        return service.extractVillesByDepartementAndPop(id, popMin, popMax);
    }
}
