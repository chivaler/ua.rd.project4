package ua.rd.project4.model.services;

import ua.rd.project4.model.dao.CarFlowDao;
import ua.rd.project4.model.dao.JdbcDaoFactory;
import ua.rd.project4.domain.CarFlow;

import java.util.List;

class JdbcCarFlowService extends CarFlowService {
    private static final JdbcCarFlowService instance = new JdbcCarFlowService();

    private JdbcCarFlowService() {
    }

    public static JdbcCarFlowService getInstance() {
        return instance;
    }

    @Override
    CarFlowDao getDao() {
        return JdbcDaoFactory.getInstance().getCarFlowDao();
    }

    @Override
    AbstractServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }

    @Override
    public List<CarFlow> findCarFlowsByClientId(int idClient) {
        return getDao().findCarFlowsByClientId(idClient);
    }

    @Override
    public List<CarFlow> findCarFlowsByCarId(int carId) {
        return getDao().findCarFlowsByClientId(carId);
    }

    @Override
    public List<CarFlow> findCarFlowsByCarRequestId(int carRequestId) {
        return getDao().findCarFlowsByClientId(carRequestId);
    }

    @Override
    public List<CarFlow> findCarFlowsByUserId(int userId) {
        return getDao().findCarFlowsByClientId(userId);
    }

    @Override
    public List<CarFlow> findCarFlowsByInvoiceId(int invoiceId) {
        return getDao().findCarFlowsByClientId(invoiceId);
    }
}
