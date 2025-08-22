package fr.diginamic.spring.demo.dtos;

import fr.diginamic.spring.demo.beans.Ville;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VilleMapper {

    /**
     * Convertit une {@link VilleDto} en {@link Ville}.
     *
     * @param villeDto La {@link VilleDto} à convertir.
     * @return l'objet {@link Ville} converti.
     */
    public Ville toEntity(VilleDto villeDto) {
        Ville ville = new Ville();
        ville.setId(villeDto.getId());
        ville.setNom(villeDto.getNom());
        ville.setCodePostal(villeDto.getCodePostal());
        ville.setNbHabitants(villeDto.getNbHabitants());
        return ville;
    }

    /**
     * Convertit une {@link Ville} en {@link VilleDto}.
     *
     * @param ville La {@link Ville} à convertir.
     * @return l'objet {@link VilleDto} converti.
     */
    public VilleDto toDto(Ville ville) {
        VilleDto villeDto = new VilleDto(ville, true);
        return villeDto;
    }

    /**
     * Convertit une liste de {@link VilleDto} en une liste de {@link Ville}.
     *
     * @param villesDto la liste de {@link VilleDto} à convertir.
     * @return la liste de {@link Ville} convertie.
     */
    public List<Ville> toEntities(List<VilleDto> villesDto) {
        List<Ville> villes = new ArrayList<>();
        for (VilleDto villeDto : villesDto) {
            villes.add(toEntity(villeDto));
        }
        return villes;
    }

    /**
     * Convertit une liste de {@link Ville} en une liste de {@link VilleDto}.
     *
     * @param villes la liste de {@link Ville} à convertir.
     * @return la liste de {@link VilleDto} convertie.
     */
    public List<VilleDto> toDtos(List<Ville> villes) {
        List<VilleDto> villesDto = new ArrayList<>();
        for (Ville ville : villes) {
            villesDto.add(toDto(ville));
        }
        return villesDto;
    }
}
