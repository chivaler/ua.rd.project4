package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.CarFlowDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.CarFlowService;

import java.util.List;

class JdbcCarFlowService extends  AbstractEntityService<CarFlow> implements CarFlowService {
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
    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
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
