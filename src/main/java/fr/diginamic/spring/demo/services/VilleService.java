package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.dtos.VilleMapper;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import fr.diginamic.spring.demo.repositories.DepartementRepository;
import fr.diginamic.spring.demo.repositories.VilleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de service pour gérer les {@link Ville}.
 */
@Service
public class VilleService {

    @Autowired
    private VilleRepository repository;

    @Autowired
    private DepartementRepository repositoryDepartement;

    @Autowired
    private VilleMapper mapper;

    /**
     * Récupère toutes les villes.
     *
     * @return Liste de {@link Ville}.
     */
    public List<VilleDto> extractVilles(PageRequest pageRequest) {
        return mapper.toDtos(repository.findAll(pageRequest).getContent());
    }

    /**
     * Récupère une {@link Ville} par son ID.
     *
     * @param id ID de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public VilleDto extractVille(int id) throws ExceptionRequeteInvalide {
        Ville ville = repository.findById(id);
        if (ville == null) {
            throw new ExceptionRequeteInvalide("Aucune ville ne correspond à l'ID");
        }
        return mapper.toDto(ville);
    }

    /**
     * Récupère toutes les villes selon le nom
     * @param nom Le nom à recherché
     * @return Liste de {@link Ville} correspondante.
     */
    public List<VilleDto> extractVillesByNom(String nom) throws ExceptionRequeteInvalide {
        List<Ville> villes = repository.findAllByNomStartingWith(nom);
        if (villes.isEmpty()) {
            throw new ExceptionRequeteInvalide("Aucune ville ne correspond au nom");
        }
        return mapper.toDtos(villes);
    }

    /**
     * Récupère toutes les villes ayant un nombre d'habitants supérieur au nombre donné
     * @param nbHabitants Le nombre d'habitants minimum
     * @return Liste de {@link Ville} correspondante.
     */
    public List<VilleDto> extractVillesByNbHabitantsGreaterThan(int nbHabitants) throws ExceptionRequeteInvalide {
        List<Ville> villes = repository.findByNbHabitantsGreaterThan(nbHabitants);
        if (villes.isEmpty()) {
            throw new ExceptionRequeteInvalide("Aucune ville ne correspond au nom");
        }
        return mapper.toDtos(villes);
    }

    /**
     * Récupère toutes les villes ayant un nombre d'habitants situé entre le min et le max
     * @param min Le nombre d'habitants minimum
     * @param max Le nombre d'habitants maximum
     * @return Liste de {@link Ville} correspondante.
     */
    public List<VilleDto> extractVillesByNbHabitantsBetween(int min, int max) throws ExceptionRequeteInvalide {
        List<Ville> villes = repository.findByNbHabitantsBetweenOrderByNbHabitantsDesc(min, max);
        if (villes.isEmpty()) {
            throw new ExceptionRequeteInvalide("Aucune ville ne correspond au nom");
        }
        return mapper.toDtos(villes);
    }

    /**
     * Récupère une {@link Ville} par son nom.
     *
     * @param nom Nom de la {@link Ville}.
     * @return {@link Ville} correspondante.
     */
    public VilleDto extractVille(String nom) throws ExceptionRequeteInvalide {
        Ville ville = repository.findByNom(nom);
        if (ville == null) {
            throw new ExceptionRequeteInvalide("Aucune ville ne correspond au nom");
        }
        return mapper.toDto(ville);
    }

    /**
     * Récupère toutes les villes d'un departement .
     *
     * @param id L'id du {@link Departement}
     * @return Liste de {@link VilleDto}.
     */
    public List<VilleDto> extractVillesByDepartement(int id) throws EntityNotFoundException {
        Departement departement = repositoryDepartement.findById(id);
        if (departement == null) {
            throw new EntityNotFoundException("Aucun département correspondant à l'ID n'a été trouvé");
        }
        return mapper.toDtos(repository.findByDepartement(departement));
    }

    /**
     * Récupère toutes les villes d'un departement ayant une population minimale de {@code popMin} .
     *
     * @param id     L'id du {@link Departement}
     * @param min La population minimale
     * @return Liste de {@link VilleDto}.
     */
    public List<VilleDto> extractVillesByDepartementAndPopMin(int id, int min) throws EntityNotFoundException{
        Departement departement = repositoryDepartement.findById(id);
        if (departement == null) {
            throw new EntityNotFoundException("Aucun département correspondant à l'ID n'a été trouvé");
        }
        List<Ville> villes = repository.findByDepartementAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(departement, min);
        List<VilleDto> villesDto = new ArrayList<>();
        for (Ville ville : villes) {
            if (ville.getNbHabitants() >= min) {
                villesDto.add(new VilleDto(ville, true));
            }
        }
        return villesDto;
    }

    /**
     * Récupère les 10 premières villes du departement par son id, triées par population.
     *
     * @param id L'id du {@link Departement}
     * @return Liste de {@link VilleDto}.
     */
    public List<VilleDto> extractNVillesByDepartement(int id, PageRequest pageRequest) throws EntityNotFoundException {
        Departement departement = repositoryDepartement.findById(id);
        if (departement == null) {
            throw new EntityNotFoundException("Aucun département correspondant à l'ID n'a été trouvé");
        }
        List<Ville> villes = repository.findAll(pageRequest).getContent();
        List<VilleDto> filteredVilles = new ArrayList<>();
        for (Ville v : villes){
            if (v.getDepartement().getId() == id) {
                filteredVilles.add(mapper.toDto(v));
            }
        }
        return filteredVilles;
    }

    /**
     * Récupère toutes les villes d'un departement ayant une population comprise entre {@code popMin} et {@code popMax}.
     *
     * @param id     L'id du {@link Departement}
     * @param popMin La population minimale
     * @param popMax La population maximale
     * @return Liste de {@link VilleDto}.
     */
    public List<VilleDto> extractVillesByDepartementAndPopBetween(int id, int popMin, int popMax) throws EntityNotFoundException {
        Departement departement = repositoryDepartement.findById(id);
        if (departement == null) {
            throw new EntityNotFoundException("Aucun département correspondant à l'ID n'a été trouvé");
        }
        List<Ville> villes = repository.findByDepartementAndNbHabitantsBetweenOrderByNbHabitantsDesc(departement, popMin, popMax);
        List<VilleDto> villesDto = new ArrayList<>();
        for (Ville ville : villes) {
            if (ville.getNbHabitants() >= popMin && ville.getNbHabitants() <= popMax) {
                villesDto.add(new VilleDto(ville, true));
            }
        }
        return villesDto;
    }

    /**
     * Ajoute une {@link Ville}.
     *
     * @param ville {@link Ville} à ajouter.
     * @return Liste des {@link Ville} après ajout ou un message d'erreur.
     */
    public List<VilleDto> insertVille(@RequestBody Ville ville) throws ExceptionRequeteInvalide {
        if (valuesAreValid(ville)) {
//            Ajout du departement selon le code postal de la ville
            Ville villeExistante = repository.findByNom(ville.getNom());
            if (villeExistante != null) {
                throw new ExceptionRequeteInvalide("Une ville portant le mâme nom existe deja");
            }
            Departement departement = findDepartementFromCode(ville);
            if (departement == null) {
                throw new ExceptionRequeteInvalide("Aucun département ne correspond au code postal de la ville");
            }
            ville.setDepartement(departement);
            repository.save(ville);
            return mapper.toDtos(repository.findAll());
        }
        throw new ExceptionRequeteInvalide("La ville n'a pas pu étre ajoutée (valeurs invalides)");
    }

    /**
     * Modifie une ville.
     *
     * @param id   ID de la ville.
     * @param data Instance de{@link Ville} avec les nouvelles données.
     * @return Liste des {@link Ville} après modification ou un message d'erreur.
     */
    @Transactional
    public List<VilleDto> modifierVille(int id, @RequestBody Ville data) throws ExceptionRequeteInvalide {
        if (data.getId() == null) {
            data.setId(id);
        }
        if (valuesAreValid(data)) {
//            Modification du departement selon le code postal de la ville
            Ville villeExistante = repository.findByNom(data.getNom());
            if (villeExistante != null && villeExistante.getId() != id) {
                throw new ExceptionRequeteInvalide("Une ville portant le même nom existe deja");
            }
            Departement departement = findDepartementFromCode(data);
            if (departement == null) {
                throw new ExceptionRequeteInvalide("Aucun département ne correspond au code postal de la ville");
            }
            data.setDepartement(departement);
            repository.save(data);
            return mapper.toDtos(repository.findAll());
        }
        throw new ExceptionRequeteInvalide("La ville n'a pas pu étre modifiée (valeurs invalides)");
    }

    /**
     * Supprime une ville.
     *
     * @param id ID de la ville.
     * @return Liste des {@link Ville} après suppression.
     */
    public List<VilleDto> supprimerVille(int id) throws ExceptionRequeteInvalide {
        Ville ville = repository.findById(id);
        if (ville == null) {
            throw new ExceptionRequeteInvalide("Aucune ville ne correspond à l'ID");
        }
        repository.deleteById(id);
        return mapper.toDtos(repository.findAll());
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
        List<Departement> departements = repositoryDepartement.findAll();
        for (Departement departement : departements) {
            if (departement.getCodeDepartement().equals(codeDepartement)) {
                ville.setDepartement(departement);
                return departement;
            }
        }
        return null;
    }
}