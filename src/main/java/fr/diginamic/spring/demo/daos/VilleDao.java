package fr.diginamic.spring.demo.daos;

import fr.diginamic.spring.demo.beans.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe DAO pour la gestion des entités {@link Ville}.
 */
@Service
public class VilleDao extends AbstractDao<Ville> {

    public VilleDao() {
        super(Ville.class);
    }

    /**
     * Renvoie une ville selon son nom.
     *
     * @param nom Le nom de la {@link Ville}.
     * @return La {@link Ville} ou une exception si elle n'existe pas.
     */
    public Ville findByNom(String nom) {
        TypedQuery<Ville> query = em.createQuery("SELECT p FROM Ville p WHERE p.nom = :nom", Ville.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    /**
     * Insère une ville dans la base de données.
     *
     * @param ville La {@link Ville} à insérer.
     * @return La liste des {@link Ville} après l'insertion.
     */
    @Transactional
    public List<Ville> insertVille(Ville ville) {
        em.persist(ville);
        return findAll();
    }

    /**
     * Met à jour une ville dans la base de données.
     *
     * @param id   L'identifiant de la {@link Ville} à mettre à jour.
     * @param data Les nouvelles données de la {@link Ville}.
     * @return La liste des {@link Ville} après la mise à jour.
     */
    @Transactional
    public List<Ville> modifierVille(int id, Ville data) {
        Ville ville = em.find(Ville.class, id);
        if (ville != null) {
            ville.setNom(data.getNom());
            ville.setNbHabitants(data.getNbHabitants());
        }
        return findAll();
    }

    /**
     * Supprime une ville de la base de données.
     *
     * @param id L'identifiant de la {@link Ville} à supprimer.
     * @return La liste des {@link Ville} après la suppression.
     */
    @Transactional
    public List<Ville> supprimerVille(int id) {
        Ville ville = em.find(Ville.class, id);
        if (ville != null) {
            em.remove(ville);
        }
        return findAll();
    }
}