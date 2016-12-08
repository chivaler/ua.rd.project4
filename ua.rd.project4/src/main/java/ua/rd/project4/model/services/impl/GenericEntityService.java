package ua.rd.project4.model.services.impl;

import ua.rd.project4.domain.Entity;
import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.EntityService;
import ua.rd.project4.model.exceptions.EntityInUseException;
import ua.rd.project4.model.exceptions.UniqueViolationException;

import java.util.List;

abstract class GenericEntityService<T extends Entity> implements EntityService<T>{
    {
        getDao().createTableIfNotExist();
    }
    abstract EntityDao<T> getDao();

    abstract ServiceFactory getServiceFactory();

    public boolean insert(T t) throws UniqueViolationException {
        return getDao().insert(t);
    }

    public boolean update(int id, T t) throws UniqueViolationException {
        return getDao().update(id, t);
    }

    public boolean delete(int id) throws EntityInUseException {
        return getDao().delete(id);
    }

    public T getById(int id) {
        return getDao().getById(id);
    }

    public List<T> findAll() {
        return getDao().findAll();
    }

}
