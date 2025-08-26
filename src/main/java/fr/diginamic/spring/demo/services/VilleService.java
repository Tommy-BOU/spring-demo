package fr.diginamic.spring.demo.services;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface VilleService {
    List<VilleDto> extractVilles(PageRequest pageRequest);

    VilleDto extractVille(int id) throws ExceptionRequeteInvalide;

    List<VilleDto> extractVillesByNom(String nom) throws ExceptionRequeteInvalide;

    List<VilleDto> extractVillesByNbHabitantsGreaterThan(int nbHabitants) throws ExceptionRequeteInvalide;

    List<VilleDto> extractVillesByNbHabitantsBetween(int min, int max) throws ExceptionRequeteInvalide;

    VilleDto extractVille(String nom) throws ExceptionRequeteInvalide;

    List<VilleDto> extractVillesByDepartement(int id) throws ExceptionRequeteInvalide;

    List<VilleDto> extractVillesByDepartementAndPopMin(int id, int min) throws ExceptionRequeteInvalide;

    List<VilleDto> extractNVillesByDepartement(int id, PageRequest pageRequest) throws ExceptionRequeteInvalide;

    List<VilleDto> extractVillesByDepartementAndPopBetween(int id, int popMin, int popMax) throws ExceptionRequeteInvalide;

    List<VilleDto> insertVille(@RequestBody Ville ville) throws ExceptionRequeteInvalide;

    @Transactional
    List<VilleDto> modifierVille(int id, @RequestBody Ville data) throws ExceptionRequeteInvalide;

    List<VilleDto> supprimerVille(int id) throws ExceptionRequeteInvalide;

    void validateValues(Ville ville) throws ExceptionRequeteInvalide;

    Departement findDepartementFromCode(Ville ville) throws ExceptionRequeteInvalide;
}
