package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DepartementService {
    List<DepartementDto> extractDepartements();

    DepartementDto extractDepartement(int id) throws ExceptionRequeteInvalide;

    DepartementDto extractDepartement(String code) throws ExceptionRequeteInvalide;

    List<DepartementDto> insertDepartement(@RequestBody Departement departement) throws ExceptionRequeteInvalide;

    List<DepartementDto> modifierDepartement(int id, @RequestBody Departement departement) throws ExceptionRequeteInvalide;

    List<DepartementDto> supprimerDepartement(int id) throws ExceptionRequeteInvalide;

    boolean valuesAreValid(Departement departement);
}
