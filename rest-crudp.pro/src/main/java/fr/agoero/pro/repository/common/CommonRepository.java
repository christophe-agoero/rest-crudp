package fr.agoero.pro.repository.common;

import java.util.List;

@SuppressWarnings({"unused", "squid:S119"})
public interface CommonRepository<T, ID> {

    //Save methods will trigger an UnsupportedOperationException
    <S extends T> S save(S entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    <S extends T> S saveAndFlush(S entity);

    <S extends T> List<S> saveAllAndFlush(Iterable<S> entities);

    //Delete methods will trigger an UnsupportedOperationException
    void deleteById(ID id);

    void delete(T entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();


    //Persist methods are meant to save newly created entities
    <S extends T> S persist(S entity);

    <S extends T> S persistAndFlush(S entity);

    <S extends T> List<S> persistAll(Iterable<S> entities);

    <S extends T> List<S> persistAllAndFlush(Iterable<S> entities);

    //Merge methods are meant to propagate detached entity state changes
    //if they are really needed
    <S extends T> S merge(S entity);

    <S extends T> S mergeAndFlush(S entity);

    <S extends T> List<S> mergeAll(Iterable<S> entities);

    <S extends T> List<S> mergeAllAndFlush(Iterable<S> entities);

}
