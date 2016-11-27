package ua.rd.project4.services;

import ua.rd.project4.Dao.CarRequestDao;
import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.CarRequest;

import java.util.List;

class JdbcCarRequestSevice extends CarRequestService {
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
    AbstractServiceFactory getServiceFactory() {
        return ServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
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
