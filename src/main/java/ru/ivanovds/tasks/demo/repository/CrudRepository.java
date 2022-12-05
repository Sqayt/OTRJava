package ru.ivanovds.tasks.demo.repository;

import java.util.List;

public interface CrudRepository<T> {

    boolean insert(T t);

    boolean update(Long id, T t);

    T findById(Long id) throws Exception;

    List<T> findAll();

    Boolean delete(Long id);

    Boolean deleteAll();
}
