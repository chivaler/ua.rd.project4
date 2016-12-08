package ua.rd.project4.model.services;

import ua.rd.project4.domain.Entity;
import ua.rd.project4.model.exceptions.EntityInUseException;
import ua.rd.project4.model.exceptions.UniqueViolationException;

import java.util.List;

public interface EntityService<T extends Entity> {

    boolean insert(T t) throws UniqueViolationException;

    boolean update(int id, T t) throws UniqueViolationException;

    boolean delete(int id) throws EntityInUseException;

    T getById(int id);

    List<T> findAll();
}
