package fr.diginamic.demo.controleurs;

import fr.diginamic.demo.beans.Ville;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    private ArrayList<Ville> villes;

    /**
     * Retourne la liste des {@link Ville}
     *
     * @return ArrayList<Ville>
     */
    @GetMapping
    public ArrayList<Ville> getVilles() {

        if (this.villes != null) {
            return this.villes;
        }
        ArrayList<Ville> villes = new ArrayList<>();

        Ville ville0 = new Ville("Paris", 1000000);
        ville0.setId(0);

        Ville ville1 = new Ville("Lyon", 800000);
        ville1.setId(1);

        Ville ville2 = new Ville("Marseille", 600000);
        ville2.setId(2);

        villes.add(ville0);
        villes.add(ville1);
        villes.add(ville2);

        this.villes = villes;
        return this.villes;
    }

    /**
     * Retourne une ville selon son id
     *
     * @param id L'identifiant de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getVille(@PathVariable int id) {

        for (Ville v : villes) {
            if (v.getId() == id) {
                return ResponseEntity.ok(v);
            }
        }
        return ResponseEntity.badRequest().body("La ressource n'a pas été trouvée");
    }

    /**
     * Ajoute une ville dans la liste
     *
     * @param newVille La ville ajoutée
     * @return ResponseEntity contenant la {@link Ville} ajoutée ou un message d'erreur
     */
    @PostMapping
    public ResponseEntity<?> ajouterVille(@Valid @RequestBody Ville newVille, BindingResult result) {

//        if (!valuesAreValid(data)) {
//            return ResponseEntity.badRequest().body("La ville n'a pas pu etre modifiee (valeurs invalides)");
//        }

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            return ResponseEntity.badRequest().body(errors.get(0).getDefaultMessage());
        }

        for (Ville ville : this.villes) {
            if (ville.getNom().equalsIgnoreCase(newVille.getNom())) {
                return ResponseEntity.badRequest().body("La ville existe deja");
            }
        }

        Ville ville = new Ville(newVille.getNom(), newVille.getNbHabitants());
        ville.setId(villes.size());
        this.villes.add(ville);

        return ResponseEntity.ok(ville);
    }

    /**
     * Modifie une {@link Ville} de la liste par son identifiant
     *
     * @param id   L'identifiant de la {@link Ville} à modifier
     * @param data La {@link Ville} modifiée
     * @return ResponseEntity contenant la {@link Ville} modifiée ou un message d'erreur
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> modifierVille(@PathVariable int id, @RequestBody Ville data) {
//        if (!valuesAreValid(data)) {
//            return ResponseEntity.badRequest().body("La ville n'a pas pu etre modifiee (valeurs invalides)");
//        }

        Errors result = validator.validateObject(data);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors().get(0).getCode() + " " + result.getFieldErrors().get(0).getDefaultMessage());
        }

        for (Ville ville : this.villes) {
            if (ville.getId() == id) {
                ville.setNom(data.getNom());
                ville.setNbHabitants(data.getNbHabitants());
                return ResponseEntity.ok(ville);
            }
        }
            return ResponseEntity.badRequest().body("La ville n'a pas pu etre modifiee");

        }

        /**
         * Supprime une {@link Ville} de la liste par son identifiant.
         *
         * @param id L'identifiant de la {@link Ville} à supprimer
         * @return ResponseEntity contenant la {@link Ville} supprimée ou un message d'erreur
         */
        @DeleteMapping(path = "/{id}")
        public ResponseEntity<?> supprimerVille ( @PathVariable int id){
            Iterator<Ville> iterator = this.villes.iterator();
            while (iterator.hasNext()) {
                Ville ville = iterator.next();
                if (ville.getId() == id) {
                    iterator.remove();
                    return ResponseEntity.ok("La ville a ete supprimee avec succès");
                }
            }

            return ResponseEntity.badRequest().body("La ville n'a pas pû être supprimée");
        }

    /**
     * Vérifie les valeurs de la {@link Ville}
     * @param ville
     * @return true si les valeurs sont valides, false sinon
     */
    public boolean valuesAreValid (Ville ville){
            return ville.getId() >= 0 && ville.getNom() != null && ville.getNom().length() >= 2 && ville.getNbHabitants() >= 1;
        }


    }
