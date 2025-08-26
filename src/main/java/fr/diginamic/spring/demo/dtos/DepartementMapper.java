package fr.diginamic.spring.demo.dtos;

import fr.diginamic.spring.demo.beans.Departement;

import java.util.List;

public interface DepartementMapper {
    Departement toEntity(DepartementDto DepartementDto);

    DepartementDto toDto(Departement Departement);

    List<Departement> toEntities(List<DepartementDto> DepartementsDto);

    List<DepartementDto> toDtos(List<Departement> Departements);
}
