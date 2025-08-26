package fr.diginamic.spring.demo.controleurs;

import com.itextpdf.text.DocumentException;
import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

public interface IDepartementControleur {
    @GetMapping
    List<DepartementDto> getDepartements();

    @GetMapping(path = "/{id}")
    DepartementDto getDepartementById(@PathVariable int id) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/code/{code}")
    DepartementDto getDepartementByCode(@PathVariable String code) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/import/{code}")
    void getDepartementPDFByCode(@PathVariable String code, HttpServletResponse response) throws ExceptionRequeteInvalide, IOException, DocumentException;

    @PostMapping
    List<DepartementDto> ajouterDepartement(@RequestBody Departement newDepartement) throws ExceptionRequeteInvalide;

    @PutMapping(path = "/{id}")
    List<DepartementDto> modifierDepartement(@PathVariable int id, @RequestBody Departement data) throws ExceptionRequeteInvalide;

    @DeleteMapping(path = "/{id}")
    List<DepartementDto> supprimerDepartement(@PathVariable int id) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/{id}/villes")
    List<VilleDto> getAllVillesByDepartement(@PathVariable int id) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/{id}/villes/{size}")
    List<VilleDto> getVillesByDepartement(@PathVariable int id, @PathVariable int size, @RequestParam int page) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/{id}/villes/pop/{popMin}")
    List<VilleDto> getVillesByDepartementAndPopMin(@PathVariable int id, @PathVariable int popMin) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/{id}/villes/pop/{popMin}/{popMax}")
    List<VilleDto> getVillesByDepartementAndPopBetween(@PathVariable int id, @PathVariable int popMin, @PathVariable int popMax) throws ExceptionRequeteInvalide;
}
