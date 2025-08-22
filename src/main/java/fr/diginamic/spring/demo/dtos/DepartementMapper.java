package fr.diginamic.spring.demo.dtos;

import fr.diginamic.spring.demo.beans.Departement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartementMapper {

    /**
     * Convertit un {@link DepartementDto} en {@link Departement}.
     *
     * @param DepartementDto Le {@link DepartementDto} à convertir.
     * @return l'objet {@link Departement} converti.
     */
    public Departement toEntity(DepartementDto DepartementDto) {
        Departement Departement = new Departement();
        Departement.setId(DepartementDto.getId());
        Departement.setNom(DepartementDto.getNom());
        Departement.setCodeDepartement(DepartementDto.getCodeDepartement());
        return Departement;
    }

    /**
     * Convertit un {@link Departement} en {@link DepartementDto}.
     *
     * @param Departement Le {@link Departement} à convertir.
     * @return l'objet {@link DepartementDto} converti.
     */
    public DepartementDto toDto(Departement Departement) {
        DepartementDto DepartementDto = new DepartementDto(Departement, true);
        return DepartementDto;
    }

    /**
     * Convertit une liste de {@link DepartementDto} en une liste de {@link Departement}.
     *
     * @param DepartementsDto la liste de {@link DepartementDto} à convertir.
     * @return la liste de {@link Departement} convertie.
     */
    public List<Departement> toEntities(List<DepartementDto> DepartementsDto) {
        List<Departement> Departements = new ArrayList<>();
        for (DepartementDto DepartementDto : DepartementsDto) {
            Departements.add(toEntity(DepartementDto));
        }
        return Departements;
    }

    /**
     * Convertit une liste de {@link Departement} en une liste de {@link DepartementDto}.
     *
     * @param Departements la liste de {@link Departement} à convertir.
     * @return la liste de {@link DepartementDto} convertie.
     */
    public List<DepartementDto> toDtos(List<Departement> Departements) {
        List<DepartementDto> DepartementsDto = new ArrayList<>();
        for (Departement Departement : Departements) {
            DepartementsDto.add(toDto(Departement));
        }
        return DepartementsDto;
    }
}
