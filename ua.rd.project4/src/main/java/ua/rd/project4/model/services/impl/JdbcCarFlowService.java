package ua.rd.project4.model.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.CarFlowDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.exceptions.WrongCarFlowDirectionException;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.CarFlowService;

import java.util.List;

class JdbcCarFlowService extends AbstractEntityService<CarFlow> implements CarFlowService {
    private static final JdbcCarFlowService instance = new JdbcCarFlowService();
    private final Logger logger = LogManager.getLogger(JdbcCarFlowService.class);

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
    public List<CarFlow> findCarFlowsByCarId(int carId) {
        return getDao().findCarFlowsByCarId(carId);
    }

    @Override
    public List<CarFlow> findCarFlowsByCarRequestId(int carRequestId) {
        return getDao().findCarFlowsByCarRequestId(carRequestId);
    }

    @Override
    public List<CarFlow> findCarFlowsByUserId(int userId) {
        return getDao().findCarFlowsByUserId(userId);
    }

    @Override
    public List<CarFlow> findCarFlowsByInvoiceId(int invoiceId) {
        return getDao().findCarFlowsByInvoiceId(invoiceId);
    }

    @Override
    public boolean checkInCarFlowOut(CarFlow carFlow) {
        if ((isCarInBox(carFlow.getCarId()) && carFlow.getCarFlowType() == CarFlow.CarFlowType.IN) ||
                (!isCarInBox(carFlow.getCarId()) && carFlow.getCarFlowType() == CarFlow.CarFlowType.OUT)) {
            throw new WrongCarFlowDirectionException();
        } else
            try {
                return insert(carFlow);
            } catch (UniqueViolationException e) {
                logger.error(e);
                return false;
            }
    }


}
