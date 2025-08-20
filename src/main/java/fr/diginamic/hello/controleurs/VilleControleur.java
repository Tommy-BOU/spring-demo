package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    private ArrayList<Ville> villes;

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

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getVille(@PathVariable int id) {

        for (Ville v : villes) {
            if (v.getId() == id) {
                return ResponseEntity.ok(v);
            }
        }
        return ResponseEntity.badRequest().body("La ressource n'a pas été trouvée");
    }

    @PostMapping
    public ResponseEntity<?> ajouterVille(@RequestBody Ville newVille) {
        for (Ville ville : this.villes) {
            if (ville.getNom().equals(newVille.getNom())) {
                return ResponseEntity.badRequest().body("La ville existe deja");
            }
        }

        Ville ville = new Ville(newVille.getNom(), newVille.getNbHabitants());
        ville.setId(villes.size());
        this.villes.add(ville);

        return ResponseEntity.ok(ville);
    }

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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> supprimerVille(@PathVariable int id) {
        for (Ville ville : this.villes) {
            if (ville.getId() == id) {
                this.villes.remove(ville);
                return ResponseEntity.ok(ville);
            }
        }
        return ResponseEntity.badRequest().body("La ville n'a pas pû être supprimée");
    }
}
