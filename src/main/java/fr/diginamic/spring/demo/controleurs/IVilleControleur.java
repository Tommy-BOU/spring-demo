package fr.diginamic.spring.demo.controleurs;

import fr.diginamic.spring.demo.beans.Ville;
import fr.diginamic.spring.demo.dtos.DepartementDto;
import fr.diginamic.spring.demo.dtos.VilleDto;
import fr.diginamic.spring.demo.exceptions.ExceptionRequeteInvalide;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IVilleControleur {

    /**
     * Retourne la liste des {@link Ville}
     *
     * @return List<Ville>
     */
    @Operation(summary = "Retourne la liste de toutes les villes avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))})
    })
    @GetMapping
    List<VilleDto> getVilles(@RequestParam int page, @RequestParam int size);

    @GetMapping(path = "/name/{nom}/all")
    List<VilleDto> getAllVilleByName(@PathVariable String nom) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/pop/{min}")
    List<VilleDto> getAllVillesPopGreaterThan(@PathVariable int min) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/pop/{min}/{max}")
    List<VilleDto> getAllVillesPopBetween(@PathVariable int min, @PathVariable int max) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/{id}")
    VilleDto getVilleById(@PathVariable int id) throws ExceptionRequeteInvalide;

    @GetMapping(path = "/name/{nom}")
    VilleDto getVilleByName(@PathVariable String nom) throws ExceptionRequeteInvalide;

    /**
     * Ajoute une ville dans la liste
     *
     * @param newVille La ville ajoutée
     * @return Liste des {@link Ville} après insertion
     */
    @Operation(summary = "Ajoute une ville et Retourne la liste de toutes les villes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))})
    })
    @PostMapping
    List<VilleDto> ajouterVille(@RequestBody Ville newVille) throws ExceptionRequeteInvalide;

    /**
     * Modifie une {@link Ville} de la liste par son identifiant
     *
     * @param id   L'identifiant de la {@link Ville} à modifier
     * @param data Les nouvelles données de la {@link Ville} sous forme d'instance
     * @return La liste des {@link Ville} apres modification
     */
    @Operation(summary = "Modifie une ville et Retourne la liste de toutes les villes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))})
    })
    @PostMapping
    @PutMapping(path = "/{id}")
    List<VilleDto> modifierVille(@PathVariable int id, @RequestBody Ville data) throws ExceptionRequeteInvalide;

    /**
     * Supprime une {@link Ville} de la liste par son identifiant.
     *
     * @param id L'identifiant de la {@link Ville} à supprimer
     * @return La liste des {@link Ville} apres suppression
     */
    @Operation(summary = "Supprimer une ville et Retourne la liste de toutes les villes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))})
    })
    @PostMapping
    @DeleteMapping(path = "/{id}")
    List<VilleDto> supprimerVille(@PathVariable int id) throws ExceptionRequeteInvalide;
}
