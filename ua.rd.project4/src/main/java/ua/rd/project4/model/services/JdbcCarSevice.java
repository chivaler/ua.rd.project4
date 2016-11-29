package ua.rd.project4.model.services;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.JdbcDaoFactory;
import ua.rd.project4.domain.Car;

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
    AbstractServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }
}
