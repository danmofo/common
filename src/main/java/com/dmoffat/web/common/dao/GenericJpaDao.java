package com.dmoffat.web.common.dao;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        CriteriaQuery<T> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);

        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }
}
