package fr.agoero.pro.repository.common.impl;

import fr.agoero.pro.repository.common.CommonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "unchecked", "squid:S119"})
public class CommonRepositoryImpl<T, ID> implements CommonRepository<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <S extends T> S save(S entity) {
        return unsupportedSave();
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return unsupportedSave();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return unsupportedSave();
    }

    @Override
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        return unsupportedSave();
    }

    protected <S extends T> S unsupportedSave() {
        throw new UnsupportedOperationException(
                "There's no such thing as a save method in JPA, so don't use this hack!"
        );
    }

    @Override
    public void deleteById(ID id) {
        throwUnsupportedOperationException();
    }

    @Override
    public void delete(T entity) {
        throwUnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        throwUnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        throwUnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throwUnsupportedOperationException();
    }

    protected void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException(
                "There's no such thing as a save method in JPA, so don't use this hack!"
        );
    }

    @Override
    public <S extends T> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <S extends T> S persistAndFlush(S entity) {
        persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public <S extends T> List<S> persistAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(persist(entity));
        }
        return result;
    }

    @Override
    public <S extends T> List<S> persistAllAndFlush(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(persist(entity));
        }
        entityManager.flush();
        return result;
    }

    @Override
    public <S extends T> S merge(S entity) {
        return entityManager.merge(entity);
    }

    @Override
    public <S extends T> S mergeAndFlush(S entity) {
        S result = merge(entity);
        entityManager.flush();
        return result;
    }

    @Override
    public <S extends T> List<S> mergeAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(merge(entity));
        }
        return result;
    }

    @Override
    public <S extends T> List<S> mergeAllAndFlush(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(merge(entity));
        }
        entityManager.flush();
        return result;
    }

}
