package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.daos.DepartementDao;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.DepartementMapper;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.dtos.VilleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao dao;

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
        return mapper.toDtos(dao.findAll());
    }

    /**
     * Récupère {@code num} villes du departement par son id.
     *
     * @param id  L'id du {@link Departement}
     * @param num Le nombre de {@link Ville} à retourner.
     * @return Liste de {@link VilleDto}.
     */
    public List<VilleDto> extractVillesByDepartement(int id, int num) {
        List<Ville> villes = dao.findAllVilleByDepartement(id);
        return villeMapper.toDtos(villes);
    }

    /**
     * Récupère toutes les villes d'un departement ayant une population comprise entre {@code popMin} et {@code popMax}.
     *
     * @param id     L'id du {@link Departement}
     * @param popMin La population minimale
     * @param popMax La population maximale
     * @return Liste de {@link VilleDto}.
     */
    public ResponseEntity<?> extractVillesByDepartementAndPop(int id, int popMin, int popMax) {
        Departement departement = dao.findById(id);
        if (departement == null) {
            return ResponseEntity.badRequest().body("Aucun departement ne correspond à l'ID");
        }
        List<Ville> villes = dao.findAllVilleByDepartement(id);
        List<VilleDto> villesDto = new ArrayList<>();
        for (Ville ville : villes) {
            if (ville.getNbHabitants() >= popMin && ville.getNbHabitants() <= popMax) {
                villesDto.add(new VilleDto(ville, true));
            }
        }
        return ResponseEntity.ok().body(villesDto);
    }

    /**
     * Récupère un {@link Departement} par son ID.
     *
     * @param id ID du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    public ResponseEntity<?> extractDepartement(int id) {
        Departement departement = dao.findById(id);
        if (departement == null) {
            return ResponseEntity.badRequest().body("Aucun departement ne correspond à l'ID");
        }
        return ResponseEntity.ok().body(mapper.toDto(departement));
    }

    /**
     * Récupère un {@link Departement} par son nom.
     *
     * @param code Le code du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    public ResponseEntity<?> extractDepartement(String code) {
        Departement departement = dao.findByCodeDepartement(code);
        if (departement == null) {
            return ResponseEntity.badRequest().body("Aucun departement ne correspond au code");
        }
        return ResponseEntity.ok().body(mapper.toDto(departement));
    }

    /**
     * Ajoute un {@link Departement}.
     *
     * @param departement {@link Departement} à ajouter.
     * @return Liste des {@link Departement} après ajout.
     */
    @PostMapping
    public ResponseEntity<?> insertDepartement(@RequestBody Departement departement) {
        if (valuesAreValid(departement)) {
            Departement departementExist = dao.findByCodeDepartement(departement.getCodeDepartement());
            if (departementExist != null) {
                return ResponseEntity.badRequest().body("Le departement existe deja");
            }
            dao.insertDepartement(departement);
            return ResponseEntity.ok().body(mapper.toDtos(dao.findAll()));
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
    @PutMapping
    public ResponseEntity<?> modifierDepartement(int id, @RequestBody Departement departement) {
        if (valuesAreValid(departement)) {
            Departement departementExist = dao.findByCodeDepartement(departement.getCodeDepartement());
            if (departementExist != null && departementExist.getId() != id) {
                return ResponseEntity.badRequest().body("Le departement existe deja");
            }
            dao.modifierDepartement(id, departement);
            return ResponseEntity.ok().body(mapper.toDtos(dao.findAll()));
        }
        return ResponseEntity.badRequest().body("Le departement n'a pas pu étre modifiée (valeurs invalides)");
    }

    /**
     * Supprime un departement.
     *
     * @param id ID du departement.
     * @return Liste des {@link Departement} après suppression.
     */
    @DeleteMapping
    public ResponseEntity<?> supprimerDepartement(int id) {
        Departement departement = dao.findById(id);
        if (departement == null) {
            return ResponseEntity.badRequest().body("Aucun departement ne correspond à l'ID");
        }
        dao.supprimerDepartement(id);
        return ResponseEntity.ok().body(mapper.toDtos(dao.findAll()));
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
