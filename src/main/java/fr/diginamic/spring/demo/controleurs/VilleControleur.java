package fr.diginamic.spring.demo.controleurs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import fr.diginamic.spring.demo.services.VilleService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Controleur des {@link Ville}
 */
@RestController
@RequestMapping("/villes")
public class VilleControleur implements IVilleControleur {

    @Autowired
    private Validator validator;

    @Autowired
    private VilleService service;

    @GetMapping
    @Override
    public List<VilleDto> getVilles(@RequestParam int page, @RequestParam int size) {
        PageRequest pagination = PageRequest.of(page, size);
        return service.extractVilles(pagination);
    }

    /**
     * Retourne une ville selon son nom
     *
     * @param nom Le nom de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/name/{nom}/all")
    @Override
    public List<VilleDto> getAllVilleByName(@PathVariable String nom) throws ExceptionRequeteInvalide {
        return service.extractVillesByNom(nom);
    }

    /**
     * Retourne la liste des villes dont la population est supÃrieure Ã  {@code min}
     *
     * @param min La population minimale
     * @return La liste des {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/pop/{min}")
    @Override
    public List<VilleDto> getAllVillesPopGreaterThan(@PathVariable int min) throws ExceptionRequeteInvalide {
        return service.extractVillesByNbHabitantsGreaterThan(min);
    }


    /**
     * Retourne la liste des villes dont la population est comprise entre {@code min} et {@code max}
     *
     * @param min La population minimale
     * @param max La population maximale
     * @return La liste des {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/pop/{min}/{max}")
    @Override
    public List<VilleDto> getAllVillesPopBetween(@PathVariable int min, @PathVariable int max) throws ExceptionRequeteInvalide {
        return service.extractVillesByNbHabitantsBetween(min, max);
    }

    /**
     * Retourne une ville selon son id
     *
     * @param id L'identifiant de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/{id}")
    @Override
    public VilleDto getVilleById(@PathVariable int id) throws ExceptionRequeteInvalide {
        return service.extractVille(id);
    }

    @GetMapping(path = "pop/{min}/import")
    @Override
    public void getVillePDFById(@PathVariable int min, HttpServletResponse response) throws IOException, DocumentException, ExceptionRequeteInvalide {
        response.setHeader("Content-Disposition", "attachment; filename=\"villes.csv\"");
        List<VilleDto> villes = service.extractVillesByNbHabitantsGreaterThan(min);

        for (VilleDto ville : villes){
            String line = ville.getNom()+";"+ville.getNbHabitants()+";"+ville.getDepartement().getCodeDepartement()+";"+ville.getDepartement().getNom()+"\n";
            response.getWriter().append(line);
        }
        response.flushBuffer();
    }

    /**
     * Retourne une ville selon son nom
     *
     * @param nom Le nom de la ville
     * @return La {@link Ville} ou un message d'erreur
     */
    @GetMapping(path = "/name/{nom}")
    @Override
    public VilleDto getVilleByName(@PathVariable String nom) throws ExceptionRequeteInvalide {
        return service.extractVille(nom);
    }

    @PostMapping
    @Override
    public List<VilleDto> ajouterVille(@RequestBody Ville newVille) throws ExceptionRequeteInvalide {
        return service.insertVille(newVille);
    }

    @PutMapping(path = "/{id}")
    @Override
    public List<VilleDto> modifierVille(@PathVariable int id, @RequestBody Ville data) throws ExceptionRequeteInvalide {
        return service.modifierVille(id, data);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public List<VilleDto> supprimerVille(@PathVariable int id) throws ExceptionRequeteInvalide {
        return service.supprimerVille(id);
    }

}
