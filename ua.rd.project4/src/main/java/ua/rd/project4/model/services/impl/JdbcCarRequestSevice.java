package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.CarRequestDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.CarRequestService;

import java.util.List;

class JdbcCarRequestSevice extends  AbstractEntityService<CarRequest> implements CarRequestService {
    private static final JdbcCarRequestSevice instance = new JdbcCarRequestSevice();

    private JdbcCarRequestSevice() {
    }

    public static JdbcCarRequestSevice getInstance() {
        return instance;
    }

    @Override
    CarRequestDao getDao() {
        return JdbcDaoFactory.getInstance().getCarRequestDao();
    }

    @Override
    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public List<CarRequest> findCarRequestsByClientId(int idClient) {
        return getDao().findCarRequestsByClientId(idClient);
    }

    @Override
    public List<CarRequest> findCarRequestsByCarId(int idClient) {
        return getDao().findCarRequestsByCarId(idClient);
    }

    @Override
    public List<CarRequest> findCarRequestsByInvoiceId(int idClient) {
        return getDao().findCarRequestsByInvoiceId(idClient);
    }
}
