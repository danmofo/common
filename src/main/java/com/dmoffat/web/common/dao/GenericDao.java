package com.dmoffat.web.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * A generic DAO interface, implement using an abstract class for the persistence provider (JPA, Hibernate, Eclipse Link, etc.)
 *
 * Created by danielmoffat on 19/03/2017.
 */
public interface GenericDao<T, PK extends Serializable> {
    void create(T entity);
    T read(PK id);
    void delete(T entity);
    T update(T entity);
    List<T> findAll();
    List<T> findAll(int offset, int rows);
    Long count();
}
