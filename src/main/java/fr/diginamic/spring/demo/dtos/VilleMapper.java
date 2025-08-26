package fr.diginamic.spring.demo.dtos;

import fr.diginamic.spring.demo.beans.Ville;

import java.util.List;

public interface VilleMapper {
    Ville toEntity(VilleDto villeDto);

    VilleDto toDto(Ville ville);

    List<Ville> toEntities(List<VilleDto> villesDto);

    List<VilleDto> toDtos(List<Ville> villes);
}
