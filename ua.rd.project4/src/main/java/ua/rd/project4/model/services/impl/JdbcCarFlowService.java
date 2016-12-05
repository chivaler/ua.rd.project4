package ua.rd.project4.model.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.dao.CarFlowDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.exceptions.WrongCarFlowDirectionException;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.CarFlowService;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


import static java.time.temporal.ChronoUnit.DAYS;

class JdbcCarFlowService extends GenericEntityService<CarFlow> implements CarFlowService {
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
    public List<CarFlow> findAll(int n) {
        return getDao().findAll(n);
    }

    @Override
    public boolean checkInCarFlowOut(CarFlow carFlow) throws WrongCarFlowDirectionException {
        if (!isCarInBox(carFlow.getCarId()) || carFlow.getCarFlowType() == CarFlow.CarFlowType.IN) {
            throw new WrongCarFlowDirectionException();
        } else
            try {
                return insert(carFlow);
            } catch (UniqueViolationException e) {
                logger.error(e);
                return false;
            }
    }

    @Override
    public void checkInCarFlowIn(int carFlowOutId, User user) throws WrongCarFlowDirectionException {
        CarFlow carFlowOut = getById(carFlowOutId);
        if (isCarInBox(carFlowOut.getCarId()) || carFlowOut.getCarFlowType() == CarFlow.CarFlowType.IN)
            throw new WrongCarFlowDirectionException();
        Invoice invoice = null;
        LocalDate current = LocalDate.now();
        LocalDate dateToFromRequest = carFlowOut.getCarRequest().getDateTo().toLocalDate();
        if (current.compareTo(dateToFromRequest) > 0) {
            long diffDays = DAYS.between(dateToFromRequest, current);
            Client client = carFlowOut.getCarRequest().getClient();
            Car car = carFlowOut.getCar();
            BigDecimal appendedInvoiceCost = car.getRentPricePerDay().multiply(new BigDecimal(diffDays));
            invoice = new Invoice(client, appendedInvoiceCost, false, "Overhead. Car:" + car + " was returned after contracted.");
            try {
                JdbcServiceFactory.getInstance().getInvoiceService().insert(invoice);
            } catch (UniqueViolationException e) {
                logger.error(e);
            }
        }
        CarFlow carFlowIn = new CarFlow(
                carFlowOut.getCar(),
                CarFlow.CarFlowType.IN,
                carFlowOut.getCarRequest(),
                user,
                invoice, "");
        try {
            insert(carFlowIn);
        } catch (UniqueViolationException e) {
            logger.error(e);
        }
    }

    @Override
    public CarFlow findLastCarFlowOfCar(int carId) {
        return findCarFlowsByCarId(carId)
                .stream()
                .filter(s -> s.getCarId() == carId)
                .filter(s -> s.getCarFlowType() == CarFlow.CarFlowType.OUT)
                .reduce((p1, p2) -> p1.getDateCreated().compareTo(p2.getDateCreated()) > 0 ? p1 : p2)
                .orElse(null);
    }
}
