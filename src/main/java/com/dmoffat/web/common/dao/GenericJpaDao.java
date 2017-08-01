package com.dmoffat.web.common.dao;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 *  Generic JPA DAO.
 *
 *  Created by danielmoffat on 19/03/2017.
 */
@Component
public abstract class GenericJpaDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> type;

    public GenericJpaDao() {}

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public T read(PK id) {
        return getEntityManager().find(type, id);
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    @Override
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    public List<T> findAll() {
        return findAll(0, 10);
    }

    @Override
    public List<T> findAll(int offset, int rows) {
        CriteriaQuery<T> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        query.setFirstResult(offset);
        query.setMaxResults(rows);
        return query.getResultList();
    }

    @Override
    public Long count() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(Long.class);

        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(type)));

        TypedQuery<Long> query = getEntityManager().createQuery(criteriaQuery);

        return query.getSingleResult();
    }

    /**
     * Shorthand helper methods to ease the writing of JPA typed queries
     */

    public CriteriaQuery<T> criteriaQuery() {
        return getEntityManager().getCriteriaBuilder().createQuery(type);
    }

    public CriteriaBuilder criteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }
}
