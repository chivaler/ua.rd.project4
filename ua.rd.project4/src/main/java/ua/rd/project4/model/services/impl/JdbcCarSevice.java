package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.Car;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.CarService;
import ua.rd.project4.model.exceptions.ExceptionEntityInUse;

class JdbcCarSevice extends GenericEntityService<Car> implements CarService {
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
    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }
}
