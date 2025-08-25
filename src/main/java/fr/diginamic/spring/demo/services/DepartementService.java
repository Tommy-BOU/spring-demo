package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.DepartementMapper;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.dtos.VilleMapper;
import fr.diginamic.spring.demo.repositories.DepartementRepository;
import fr.diginamic.spring.demo.repositories.VilleRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository repository;

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementMapper mapper;

    @Autowired
    private VilleMapper villeMapper;

    /**
     * Récupère tout les departements.
     *
     * @return Liste de {@link Departement}.
     */
    public List<DepartementDto> extractDepartements() {
        return mapper.toDtos(repository.findAll());
    }


    /**
     * Récupère un {@link Departement} par son ID.
     *
     * @param id ID du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    public ResponseEntity<?> extractDepartement(int id) throws EntityNotFoundException {
        Departement departement = repository.findById(id);
        if (departement == null) {
            throw new EntityNotFoundException("Aucun département correspondant à l'ID n'a été trouvé");
        }
        return ResponseEntity.ok().body(mapper.toDto(departement));
    }

    /**
     * Récupère un {@link Departement} par son nom.
     *
     * @param code Le code du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    public ResponseEntity<?> extractDepartement(String code) throws EntityNotFoundException {
        Departement departement = repository.findByCodeDepartement(code);
        if (departement == null) {
            throw new EntityNotFoundException("Aucun département correspondant au code n'a été trouvé");
        }
        return ResponseEntity.ok().body(mapper.toDto(departement));
    }

    /**
     * Ajoute un {@link Departement}.
     *
     * @param departement {@link Departement} à ajouter.
     * @return Liste des {@link Departement} après ajout.
     */
    public ResponseEntity<?> insertDepartement(@RequestBody Departement departement) throws EntityExistsException {
        if (valuesAreValid(departement)) {
            Departement departementExist = repository.findByCodeDepartement(departement.getCodeDepartement());
            if (departementExist != null) {
                throw new EntityExistsException("Le département existe déjà");
            }
            repository.save(departement);
            return ResponseEntity.ok().body(mapper.toDtos(repository.findAll()));
        }
        return ResponseEntity.badRequest().body("Le departement n'a pas pu étre ajoutée (valeurs invalides)");
    }

    /**
     * Modifie un departement.
     *
     * @param id          ID du departement.
     * @param departement {@link Departement} avec les nouvelles données.
     * @return Liste des {@link Departement} après modification.
     */
    public ResponseEntity<?> modifierDepartement(int id, @RequestBody Departement departement) throws EntityExistsException {
        if (valuesAreValid(departement)) {
            Departement departementExist = repository.findByCodeDepartement(departement.getCodeDepartement());
            if (departementExist != null && departementExist.getId() != id) {
                throw new EntityExistsException("Le département existe déjà");
            }
            repository.save(departement);
            return ResponseEntity.ok().body(mapper.toDtos(repository.findAll()));
        }
        return ResponseEntity.badRequest().body("Le departement n'a pas pu étre modifiée (valeurs invalides)");
    }

    /**
     * Supprime un departement.
     *
     * @param id ID du departement.
     * @return Liste des {@link Departement} après suppression.
     */
    public ResponseEntity<?> supprimerDepartement(int id) throws EntityNotFoundException {
        Departement departement = repository.findById(id);
        if (departement == null) {
            throw new EntityNotFoundException("Aucun département correspondant à l'ID n'a été trouvé");
        }
        repository.deleteById(id);
        return ResponseEntity.ok().body(mapper.toDtos(repository.findAll()));
    }

    /**
     * Vérifie les valeurs du {@link Departement}
     *
     * @param departement Le {@link Departement} à vérifier.
     * @return true si les valeurs sont valides, false sinon
     */
    public boolean valuesAreValid(Departement departement) {
        if (departement.getId() != null) {
            return departement.getId() >= 0 && departement.getNom() != null && departement.getNom().length() >= 2 && departement.getCodeDepartement() != null;
        }
        return departement.getNom() != null && departement.getNom().length() >= 2 && departement.getCodeDepartement() != null;
    }
}
