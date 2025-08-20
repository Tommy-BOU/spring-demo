package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @GetMapping(path = "/all")
    public List<Ville> getVilles() {
        List<Ville> villes = new java.util.ArrayList<>();
        villes.add(new Ville("Paris", 1000000));
        villes.add(new Ville("Lyon", 800000));
        villes.add(new Ville("Marseille", 600000));
        return villes;
    }
}
