package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    private List<Ville> villes;

    @GetMapping
    public List<Ville> getVilles() {
        List<Ville> villes = new java.util.ArrayList<>();

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
        for (Ville ville : getVilles()) {
            if (ville.getNom().equals(newVille.getNom())) {
                return ResponseEntity.badRequest().body("La ville existe deja");
            }
        }

        Ville ville = new Ville(newVille.getNom(), newVille.getNbHabitants());
        ville.setId(villes.size());
        this.villes.add(ville);

        return ResponseEntity.ok(ville);
    }
}
