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

    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public T findById(int id) {
        return em.find(entityClass, id);
    }
}
