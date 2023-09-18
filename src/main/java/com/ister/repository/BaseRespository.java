package com.ister.repository;

import com.ister.domain.User;

import java.util.List;
import java.util.Optional;

public interface BaseRespository<T,PK> {
    boolean create(T entity);

    boolean delete(T entity);

    boolean update(T entity);

    List<T> getAll();

    Optional<T> findById(PK id);


}
