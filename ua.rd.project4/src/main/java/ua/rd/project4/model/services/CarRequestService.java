package ua.rd.project4.model.services;

import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.CarRequestApproveNeededException;
import ua.rd.project4.model.exceptions.CarRequestPaymentNeededException;
import ua.rd.project4.model.exceptions.ConflictsRequestException;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.holders.InvoiceHolder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface CarRequestService extends EntityService<CarRequest> {
    List<CarRequest> findCarRequestsByClientId(int clientId);

    List<CarRequest> findCarRequestsByCarId(int carId);

    List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);

    enum CarRequestStatus {
        POSSIBLE, CONFLICT, IMPOSSIBLE;
    }

    default CarRequestStatus isPossible(int carRequestId) {
        CarRequest carRequest = getById(carRequestId);
        Supplier<Stream<CarRequest>> possibleConflicted =
                () -> findAll().stream()
                        .filter(s -> s.getId() != carRequestId)
                        .filter(s -> s.getCarId() == carRequest.getCarId())
                        .filter(s -> s.getStatus() != CarRequest.RequestStatus.REJECTED)
                        .filter(s -> (s.getDateTo().compareTo(carRequest.getDateFrom()) > 0
                                && s.getDateFrom().compareTo(carRequest.getDateTo()) < 0));
        if (!possibleConflicted.get().findAny().isPresent())
            return CarRequestStatus.POSSIBLE;
        if (possibleConflicted.get().noneMatch(s -> s.getStatus() != CarRequest.RequestStatus.NEW))
            return CarRequestStatus.CONFLICT;
        else return CarRequestStatus.IMPOSSIBLE;
    }

    default List<Map<String, String>> getCarRequestsWithStatuses() {
        return findAll().stream()
                .filter(s -> s.getStatus() != CarRequest.RequestStatus.DONE)
                .map(s -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", String.valueOf(s.getId()));
                    map.put("status", String.valueOf(s.getStatus()));
                    map.put("carId", String.valueOf(s.getCarId()));
                    map.put("carStr", String.valueOf(s.getCarId() > 0 ? s.getCar().toString() : ""));
                    map.put("clientId", String.valueOf(s.getClientId()));
                    map.put("clientStr", s.getClient().toString());
                    map.put("available", String.valueOf(isPossible(s.getId())));
                    map.put("dateTo", String.valueOf(s.getDateTo()));
                    map.put("dateFrom", String.valueOf(s.getDateFrom()));
                    map.put("dateCreated", String.valueOf(s.getDateCreated()));
                    if (s.getInvoiceId() > 0)
                        map.put("paid", String.valueOf(s.getInvoice().isPaid()));
                    else
                        map.put("paid", "false");
                    return map;
                })
                .collect(Collectors.toList());
    }

    void approve(int carRequestId) throws ConflictsRequestException;

    void reject(int carRequestId, String reason);

    BigDecimal calculateTotal(int carRequestId);

    void checkInCarOut(int carRequestId, User user) throws CarRequestApproveNeededException, CarRequestPaymentNeededException;

    }
