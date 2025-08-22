package fr.diginamic.spring.demo.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public abstract class AbstractDao<T> {

    private final Class<T> entityClass;

    @PersistenceContext
    protected EntityManager em;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Renvoie tous les enregistrements de la table correspondant à l'entité de la variable d'instance {@code entityClass}.
     * @return La liste des enregistrements.
     */
    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    /**
     * Renvoie un enregistrement de la table dont l'id correspond au parametre {@code id} et qui appartient à l'entité de la variable d'instance {@code entityClass}.
     * @param id L'id de l'enregistrement.
     * @return L'enregistrement.
     */
    public T findById(int id) {
        return em.find(entityClass, id);
    }
}
