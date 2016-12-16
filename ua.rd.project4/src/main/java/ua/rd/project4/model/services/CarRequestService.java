package ua.rd.project4.model.services;

import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.CarRequestApproveNeededException;
import ua.rd.project4.model.exceptions.CarRequestPaymentNeededException;
import ua.rd.project4.model.exceptions.ConflictsRequestException;
import ua.rd.project4.model.exceptions.PaymentExistException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public interface CarRequestService extends EntityService<CarRequest> {
    enum CarRequestStatus {
        POSSIBLE, CONFLICT, IMPOSSIBLE
    }

    List<CarRequest> findCarRequestsByClientId(int clientId);

    List<CarRequest> findCarRequestsByCarId(int carId);

    List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);

    List<Car> findAvailableCars(Date dateFrom, Date dateTo);

    CarRequestStatus isPossible(int carRequestId);

    default List<Map<String, String>> getCarRequestsWithStatuses() {
        return findAll().stream()
                .filter(s -> s.getStatus() != CarRequest.RequestStatus.DONE)
                .map(s -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", String.valueOf(s.getId()));
                    map.put("status", String.valueOf(s.getStatus()));
                    map.put("carId", String.valueOf(s.getCarId()));
                    map.put("carStr", s.getCarId() > 0 ? s.getCar().toString() : "");
                    map.put("clientId", String.valueOf(s.getClientId()));
                    map.put("clientStr", s.getClientId() > 0 ? s.getClient().toString() : "");
                    map.put("available", String.valueOf(isPossible(s.getId())));
                    map.put("dateTo", String.valueOf(s.getDateTo()));
                    map.put("dateFrom", String.valueOf(s.getDateFrom()));
                    map.put("dateCreated", String.valueOf(s.getDateCreated()));
                    if (s.getInvoiceId() > 0) {
                        map.put("paid", String.valueOf(s.getInvoice().isPaid()));
                        map.put("invoiceId", String.valueOf(s.getInvoiceId()));
                    } else
                        map.put("paid", "false");
                    return map;
                })
                .collect(Collectors.toList());
    }

    void approve(int carRequestId) throws ConflictsRequestException;

    void reject(int carRequestId, String reason) throws PaymentExistException;

    BigDecimal calculateTotal(CarRequest carRequest);

    void checkInCarOut(int carRequestId, User user) throws CarRequestApproveNeededException, CarRequestPaymentNeededException;

    CarRequestStatus isDatesAvalable(Date dateFrom, Date dateTo, int carId, int excludedCarRequest);
}
