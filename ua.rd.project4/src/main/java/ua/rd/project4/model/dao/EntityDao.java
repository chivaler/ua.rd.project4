package ua.rd.project4.model.dao;


import ua.rd.project4.domain.Entity;

import java.util.List;

public interface EntityDao<T extends Entity> {
    void createTableIfNotExist();

    boolean insert(T t);

    boolean update(int id, T t);

    boolean delete(int id);

    T getById(int id);

    List<T> findAll();

}
