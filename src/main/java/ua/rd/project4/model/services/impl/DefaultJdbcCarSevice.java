package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.Car;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.CarService;

class DefaultJdbcCarSevice extends GenericEntityService<Car> implements CarService {
    private static final DefaultJdbcCarSevice instance = new DefaultJdbcCarSevice();

    private DefaultJdbcCarSevice() {
    }

    public static DefaultJdbcCarSevice getInstance() {
        return instance;
    }

    @Override
    EntityDao<Car> getDao() {
        return JdbcDaoFactory.getInstance().getCarDao();
    }

    @Override
    ServiceFactory getServiceFactory() {
        return DefaultServiceFactory.getInstance();
    }
}


