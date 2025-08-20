package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    private ArrayList<Ville> villes;

    /**
     * Retourne la liste des {@link Ville}
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
     * @param newVille La ville ajoutée
     * @return ResponseEntity contenant la {@link Ville} ajoutée ou un message d'erreur
     */
    @PostMapping
    public ResponseEntity<?> ajouterVille(@RequestBody Ville newVille) {
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
     * @param id L'identifiant de la {@link Ville} à modifier
     * @param data La {@link Ville} modifiée
     * @return ResponseEntity contenant la {@link Ville} modifiée ou un message d'erreur
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> modifierVille(@PathVariable int id, @RequestBody Ville data) {
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
    public ResponseEntity<?> supprimerVille(@PathVariable int id) {
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

}
