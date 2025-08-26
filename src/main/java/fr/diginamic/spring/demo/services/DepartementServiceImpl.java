package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.DepartementMapper;
import fr.diginamic.spring.demo.dtos.VilleMapper;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import fr.diginamic.spring.demo.repositories.DepartementRepository;
import fr.diginamic.spring.demo.repositories.VilleRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DepartementServiceImpl implements DepartementService {

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
    @Override
    public List<DepartementDto> extractDepartements() {
        return mapper.toDtos(repository.findAll());
    }


    /**
     * Récupère un {@link Departement} par son ID.
     *
     * @param id ID du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    @Override
    public DepartementDto extractDepartement(int id) throws ExceptionRequeteInvalide {
        Departement departement = repository.findById(id);
        if (departement == null) {
            throw new ExceptionRequeteInvalide("Aucun département correspondant à l'ID n'a été trouvé");
        }
        return mapper.toDto(departement);
    }

    /**
     * Récupère un {@link Departement} par son nom.
     *
     * @param code Le code du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    @Override
    public DepartementDto extractDepartement(String code) throws ExceptionRequeteInvalide {
        Departement departement = repository.findByCodeDepartement(code);
        if (departement == null) {
            throw new ExceptionRequeteInvalide("Aucun département correspondant au code n'a été trouvé");
        }
        return mapper.toDto(departement);
    }

    /**
     * Ajoute un {@link Departement}.
     *
     * @param departement {@link Departement} à ajouter.
     * @return Liste des {@link Departement} après ajout.
     */
    @Override
    public List<DepartementDto> insertDepartement(@RequestBody Departement departement) throws ExceptionRequeteInvalide {
        if (valuesAreValid(departement)) {
            Departement departementExist = repository.findByCodeDepartement(departement.getCodeDepartement());
            if (departementExist != null) {
                throw new ExceptionRequeteInvalide("Le département existe déjà");
            }
            repository.save(departement);
            return mapper.toDtos(repository.findAll());
        }
        throw new ExceptionRequeteInvalide("Le departement n'a pas pu étre ajoutée (valeurs invalides)");
    }

    /**
     * Modifie un departement.
     *
     * @param id          ID du departement.
     * @param departement {@link Departement} avec les nouvelles données.
     * @return Liste des {@link Departement} après modification.
     */
    @Override
    public List<DepartementDto> modifierDepartement(int id, @RequestBody Departement departement) throws ExceptionRequeteInvalide {
        if (valuesAreValid(departement)) {
            Departement departementExist = repository.findByCodeDepartement(departement.getCodeDepartement());
            if (departementExist != null && departementExist.getId() != id) {
                throw new ExceptionRequeteInvalide("Le département existe déjà");
            }
            repository.save(departement);
            return mapper.toDtos(repository.findAll());
        }
        throw new ExceptionRequeteInvalide("Le departement n'a pas pu étre ajoutée (valeurs invalides)");
    }

    /**
     * Supprime un departement.
     *
     * @param id ID du departement.
     * @return Liste des {@link Departement} après suppression.
     */
    @Override
    public List<DepartementDto> supprimerDepartement(int id) throws ExceptionRequeteInvalide {
        Departement departement = repository.findById(id);
        if (departement == null) {
            throw new ExceptionRequeteInvalide("Aucun département correspondant à l'ID n'a été trouvé");
        }
        repository.deleteById(id);
        return mapper.toDtos(repository.findAll());
    }

    /**
     * Vérifie les valeurs du {@link Departement}
     *
     * @param departement Le {@link Departement} à vérifier.
     * @return true si les valeurs sont valides, false sinon
     */
    @Override
    public boolean valuesAreValid(Departement departement) {
        if (departement.getId() != null) {
            return departement.getId() >= 0 && departement.getNom() != null && departement.getNom().length() >= 2 && departement.getCodeDepartement() != null;
        }
        return departement.getNom() != null && departement.getNom().length() >= 2 && departement.getCodeDepartement() != null;
    }
}
