package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.daos.DepartementDao;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.VilleDto;
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

    /**
     * Récupère tout les departements.
     *
     * @return Liste de {@link Departement}.
     */
    public List<DepartementDto> extractDepartements() {
        List<Departement> departements = dao.findAll();
        List<DepartementDto> departementsDto = new ArrayList<>();
        for (Departement departement : departements) {
            departementsDto.add(new DepartementDto(departement, true));
        }
        return departementsDto;
    }

    /**
     *  Récupère {@code num} villes du departement par son id.
     * @param id L'id du {@link Departement}
     * @param num Le nombre de {@link Ville} à retourner.
     * @return Liste de {@link VilleDto}.
     */
    public List<VilleDto> extractVillesByDepartement(int id, int num) {
        List<Ville> villes = dao.findAllByDepartement(id);
        List<VilleDto> villesDto = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            villesDto.add(new VilleDto(villes.get(i), true));
        }
        return villesDto;
    }

    /**
     * Récupère un {@link Departement} par son ID.
     *
     * @param id ID du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    public DepartementDto extractDepartement(int id) {
        Departement departement = dao.findById(id);
        return new DepartementDto(departement, true);
    }

    /**
     * Récupère un {@link Departement} par son nom.
     *
     * @param code Le code du {@link Departement}.
     * @return {@link Departement} correspondant.
     */
    public DepartementDto extractDepartement(String code) {
        Departement departement = dao.findByCodeDepartement(code);
        return new DepartementDto(departement, true);
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
            List<Departement> departements= dao.insertDepartement(departement);
            List<DepartementDto> departementsDto = new ArrayList<>();
            for (Departement dep : departements) {
                departementsDto.add(new DepartementDto(dep, true));
            }
            return ResponseEntity.ok().body(departementsDto);
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
            List<Departement> departements= dao.modifierDepartement(id, departement);
            List<DepartementDto> departementsDto = new ArrayList<>();
            for (Departement dep : departements) {
                departementsDto.add(new DepartementDto(dep, true));
            }
            return ResponseEntity.ok().body(departementsDto);
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
    public List<DepartementDto> supprimerDepartement(int id) {
        List<Departement> departements = dao.supprimerDepartement(id);
        List<DepartementDto> departementsDto = new ArrayList<>();
        for (Departement dep : departements) {
            departementsDto.add(new DepartementDto(dep, true));
        }
        return departementsDto;
    }

    /**
     * Vérifie les valeurs de la {@link Departement}
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
