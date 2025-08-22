package fr.diginamic.spring.demo.daos;

import fr.diginamic.spring.demo.beans.Departement;
import fr.diginamic.spring.demo.beans.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementDao extends AbstractDao<Departement> {

    public DepartementDao() {
        super(Departement.class);
    }
    /**
     * Renvoie un departement selon son nom.
     *
     * @param code Le code du {@link Departement}.
     * @return Le {@link Departement}.
     */
    public Departement findByCodeDepartement(String code) {
        TypedQuery<Departement> query = em.createQuery("SELECT p FROM Departement p WHERE p.codeDepartement = :code", Departement.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }

    /**
     * Renvoie toutes d'un département.
     * @param idDepartement L'id du departement
     * @return La liste des {@link Ville}
     */
    public List<Ville> findAllByDepartement(int idDepartement) {
        TypedQuery<Ville> query = em.createQuery("SELECT p FROM Ville p WHERE p.departement.id = :idDepartement ORDER BY p.nbHabitants DESC", Ville.class);
        query.setParameter("idDepartement", idDepartement);
        return query.getResultList();
    }

    /**
     * Insère un departement dans la base de données.
     *
     * @param departement Le {@link Departement} à insérer.
     * @return La liste des {@link Departement} après l'insertion.
     */
    @Transactional
    public List<Departement> insertDepartement(Departement departement) {
        em.persist(departement);
        return findAll();
    }

    /**
     * Met à jour un departement dans la base de données.
     *
     * @param id   L'identifiant du {@link Departement} à mettre à jour.
     * @param data Les nouvelles données du {@link Departement}.
     * @return La liste des {@link Departement} après la mise à jour.
     */
    @Transactional
    public List<Departement> modifierDepartement(int id, Departement data) {
        Departement dep = em.find(Departement.class, id);
        if (dep != null) {
            dep.setNom(data.getNom());
            dep.setCodeDepartement(data.getCodeDepartement());
        }
        return findAll();
    }

    /**
     * Supprime un departement de la base de données.
     *
     * @param id L'identifiant du {@link Departement} à supprimer.
     * @return La liste des {@link Departement} après la suppression.
     */
    @Transactional
    public List<Departement> supprimerDepartement(int id) {
        Departement dep = em.find(Departement.class, id);
        if (dep != null) {
            em.remove(dep);
        }
        return findAll();
    }
}
