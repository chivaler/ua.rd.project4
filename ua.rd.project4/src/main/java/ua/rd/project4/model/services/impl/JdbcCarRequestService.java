package ua.rd.project4.model.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.dao.CarDao;
import ua.rd.project4.model.dao.CarRequestDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.exceptions.*;
import ua.rd.project4.model.services.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

class JdbcCarRequestService extends GenericEntityService<CarRequest> implements CarRequestService {
    private static final JdbcCarRequestService instance = new JdbcCarRequestService();
    private final Logger logger = LogManager.getLogger(JdbcCarRequestService.class);
    private final InvoiceService invoiceService = JdbcServiceFactory.getInstance().getInvoiceService();
    private final CarFlowService carFlowService = JdbcServiceFactory.getInstance().getCarFlowService();
    private final CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();

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
    public List<Car> findAvailableCars(Date dateFrom, Date dateTo) {
        return carDao.findAvailableCars(dateFrom, dateTo);
    }

    @Override
    public CarRequestStatus isPossible(int carRequestId) {
        CarRequest carRequest = getById(carRequestId);
        return isDatesAvalable(carRequest.getDateFrom(), carRequest.getDateTo(), carRequest.getCarId(), carRequest.getId());
    }

    @Override
    public CarRequestStatus isDatesAvalable(Date dateFrom, Date dateTo, int carId, int excludedCarRequest) {
        List<CarRequest> possibleConflicted = getDao().findConflictingCarRequests(dateFrom, dateTo, carId, excludedCarRequest);
        if (possibleConflicted.isEmpty())
            return CarRequestStatus.POSSIBLE;
        if (possibleConflicted.stream().noneMatch(s -> s.getStatus() != CarRequest.RequestStatus.NEW))
            return CarRequestStatus.CONFLICT;
        else return CarRequestStatus.IMPOSSIBLE;
    }

    @Override
    public synchronized void approve(int carRequestId) throws ConflictsRequestException {
        CarRequest carRequest = getById(carRequestId);
        if (carRequest.getStatus() != CarRequest.RequestStatus.APPROVED) {
            if (isPossible(carRequestId) != CarRequestStatus.IMPOSSIBLE) {
                carRequest.setStatus(CarRequest.RequestStatus.APPROVED);
                try {
                    update(carRequestId, carRequest);
                } catch (UniqueViolationException e) {
                    logger.error(e);
                }
                BigDecimal total = carRequest.getTotalCost();
                if (total.compareTo(BigDecimal.ZERO) == 0)
                    total = calculateTotal(carRequest);
                Invoice invoice = new Invoice(carRequest.getClient(),
                        total,
                        false,
                        "Invoice for rent " + carRequest.getCar());
                try {
                    invoiceService.insert(invoice);
                    carRequest.setInvoice(invoice);
                    carRequest.setTotalCost(total);
                    update(carRequestId, carRequest);
                } catch (UniqueViolationException e) {
                    logger.error(e);
                }
            } else
                throw new ConflictsRequestException();
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

    @Override
    public BigDecimal calculateTotal(CarRequest carRequest) {
        long diff = carRequest.getDateTo().getTime() - carRequest.getDateFrom().getTime();
        long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        return carRequest.getCar().getRentPricePerDay()
                .multiply(new BigDecimal(diffDays));
    }

    @Override
    public void checkInCarOut(int carRequestId, User user) throws CarRequestApproveNeededException, CarRequestPaymentNeededException {
        CarRequest carRequest = getById(carRequestId);
        if (carRequest.getStatus() != CarRequest.RequestStatus.APPROVED)
            throw new CarRequestApproveNeededException();
        if (carRequest.getInvoiceId() == 0 || !carRequest.getInvoice().isPaid())
            throw new CarRequestPaymentNeededException();
        CarFlow carFlow = new CarFlow(carRequest.getCar(), CarFlow.CarFlowType.OUT, carRequest, user, null, "");
        try {
            carFlowService.checkInCarFlowOut(carFlow);
            carRequest.setStatus(CarRequest.RequestStatus.PROGRESS);
            update(carRequestId, carRequest);
        } catch (UniqueViolationException | WrongCarFlowDirectionException e) {
            logger.error(e);
        }
    }

}
