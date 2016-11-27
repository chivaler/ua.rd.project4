package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.entities.Entity;

import java.util.List;

class ExceptionEntityInUse extends Exception {

}

abstract class EntityService<T extends Entity> {
    abstract EntityDao<T> getDao();

    public boolean insert(T t) {
        return getDao().insert(t);
    }

    public boolean update(int id, T t){
        return getDao().update(id, t);
    }

    public boolean delete(int id) throws ExceptionEntityInUse {
        return getDao().delete(id);
    }

    public T getById(int id){
        return getDao().getById(id);
    }

    public List<T> findAll(){
        return getDao().findAll();
    }

    public Integer findId(T t){
        return getDao().findId(t);
    }
}
