package fr.diginamic.spring.demo.controleurs;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import fr.diginamic.spring.demo.services.DepartementService;
import fr.diginamic.spring.demo.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controleur des {@link Departement}
 */
@RestController
@RequestMapping("/departements")
public class DepartementControleur implements IDepartementControleur {
    @Autowired
    private Validator validator;

    @Autowired
    private DepartementService service;

    @Autowired
    private VilleService villeServiceImpl;

    /**
     * Retourne la liste des {@link Departement}
     *
     * @return List<Departement>
     */
    @GetMapping
    @Override
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
    @Override
    public DepartementDto getDepartementById(@PathVariable int id) throws ExceptionRequeteInvalide {
        return service.extractDepartement(id);
    }

    /**
     * Retourne un departement selon son nom
     *
     * @param code Le code du departement
     * @return Le {@link Departement} ou un message d'erreur
     */
    @GetMapping(path = "/code/{code}")
    @Override
    public DepartementDto getDepartementByCode(@PathVariable String code) throws ExceptionRequeteInvalide {
        return service.extractDepartement(code);
    }

    /**
     * Ajoute un departement dans la liste
     *
     * @param newDepartement Le departement ajoutée
     * @return Liste des {@link Departement} après insertion
     */
    @PostMapping
    @Override
    public List<DepartementDto> ajouterDepartement(@RequestBody Departement newDepartement) throws ExceptionRequeteInvalide {
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
    @Override
    public List<DepartementDto> modifierDepartement(@PathVariable int id, @RequestBody Departement data) throws ExceptionRequeteInvalide {

        return service.modifierDepartement(id, data);

    }

    /**
     * Supprime un {@link Departement} de la liste par son identifiant.
     *
     * @param id L'identifiant de la {@link Departement} à supprimer
     * @return La liste des {@link Departement} apres suppression
     */
    @DeleteMapping(path = "/{id}")
    @Override
    public List<DepartementDto> supprimerDepartement(@PathVariable int id) throws ExceptionRequeteInvalide {
        return service.supprimerDepartement(id);
    }

    /**
     * Retourne la liste des villes d'un departement
     * @param id L'id du {@link Departement}
     * @return List<VilleDto>
     */
    @GetMapping(path = "/{id}/villes")
    @Override
    public List<VilleDto> getAllVillesByDepartement(@PathVariable int id) throws ExceptionRequeteInvalide{
        return villeServiceImpl.extractVillesByDepartement(id);
    }

    /**
     * Retourne la liste des villes d'un departement contenant {@code num} villes
     * @param id L'id du {@link Departement}
     * @param size Le nombre de villes à retourner
     * @return List<VilleDto>
     */
    @GetMapping(path = "/{id}/villes/{size}")
    @Override
    public List<VilleDto> getVillesByDepartement(@PathVariable int id, @PathVariable int size, @RequestParam int page) throws ExceptionRequeteInvalide {
        PageRequest pagination = PageRequest.of(page, size);
        return villeServiceImpl.extractNVillesByDepartement(id, pagination);
    }

    /**
     * Retourne la liste des villes d'un departement ayant une population supérieur à {@code popMin}
     * @param id L'id du {@link Departement}
     * @param popMin La population minimale
     * @return List<VilleDto>
     */
    @GetMapping(path = "/{id}/villes/pop/{popMin}")
    @Override
    public List<VilleDto> getVillesByDepartementAndPopMin(@PathVariable int id, @PathVariable int popMin) throws ExceptionRequeteInvalide{
        return villeServiceImpl.extractVillesByDepartementAndPopMin(id, popMin);
    }

    /**
     * Retourne la liste des villes d'un departement dont la population est comprise entre {@code popMin} et {@code popMax}
     * @param id L'id du {@link Departement}
     * @param popMin La population minimale
     * @param popMax La population maximale
     * @return une List<VilleDto>
     */
    @GetMapping(path = "/{id}/villes/pop/{popMin}/{popMax}")
    @Override
    public List<VilleDto> getVillesByDepartementAndPopBetween(@PathVariable int id, @PathVariable int popMin, @PathVariable int popMax) throws ExceptionRequeteInvalide{
        return villeServiceImpl.extractVillesByDepartementAndPopBetween(id, popMin, popMax);
    }
}
