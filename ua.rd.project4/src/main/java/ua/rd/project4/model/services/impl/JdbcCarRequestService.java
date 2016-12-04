package ua.rd.project4.model.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.dao.CarRequestDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.CarRequestService;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

class JdbcCarRequestService extends AbstractEntityService<CarRequest> implements CarRequestService {
    private static final JdbcCarRequestService instance = new JdbcCarRequestService();
    private final Logger logger = LogManager.getLogger(JdbcCarRequestService.class);

    private JdbcCarRequestService() {
    }

    public static JdbcCarRequestService getInstance() {
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

    @Override
    public void approve(int carRequestId) {
        CarRequest carRequest = getById(carRequestId);
        if (carRequest.getStatus() != CarRequest.RequestStatus.APPROVED) {
            carRequest.setStatus(CarRequest.RequestStatus.APPROVED);
            try {
                update(carRequestId, carRequest);
            } catch (UniqueViolationException e) {
                logger.error(e);
            }
            Invoice invoice = new Invoice(carRequest.getClient(),
                    calculateTotal(carRequestId),
                    false,
                    "Invoice for rent " + carRequest.getCar());
            try {
                JdbcInvoiceSevice.getInstance().insert(invoice);
            } catch (UniqueViolationException e) {
                logger.error(e);
            }
        }
    }


    @Override
    public void reject(int carRequestId, String reason) {
        CarRequest carRequest = getById(carRequestId);
        carRequest.setStatus(CarRequest.RequestStatus.REJECTED);
        carRequest.setRejectReason(reason);
        try {
            update(carRequestId, carRequest);
        } catch (UniqueViolationException e) {
            logger.error(e);
        }
    }

    public BigDecimal calculateTotal(int carRequestId) {
        CarRequest carRequest = getById(carRequestId);
        long diff = carRequest.getDateTo().getTime() - carRequest.getDateFrom().getTime();
        long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        return carRequest.getCar().getRentPricePerDay()
                .multiply(new BigDecimal(diffDays));
    }
}
