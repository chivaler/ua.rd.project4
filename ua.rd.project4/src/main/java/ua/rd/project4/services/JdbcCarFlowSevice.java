package ua.rd.project4.services;

import ua.rd.project4.Dao.CarFlowDao;
import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.CarFlow;

import java.util.List;

class JdbcCarFlowSevice extends CarFlowService {
    private static final JdbcCarFlowSevice instance = new JdbcCarFlowSevice();

    private JdbcCarFlowSevice() {
    }

    public static JdbcCarFlowSevice getInstance() {
        return instance;
    }

    @Override
    CarFlowDao getDao() {
        return JdbcDaoFactory.getInstance().getCarFlowDao();
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
