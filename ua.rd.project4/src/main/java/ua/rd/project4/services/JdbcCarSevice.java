package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.Car;

class JdbcCarSevice extends CarService {
    private static final JdbcCarSevice instance = new JdbcCarSevice();
    private JdbcCarSevice() {
    }

    public static JdbcCarSevice getInstance() {
        return instance;
    }

    @Override
    EntityDao<Car> getDao() {
        return JdbcDaoFactory.getInstance().getCarDao();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }
}
