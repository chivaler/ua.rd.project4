package ua.rd.project4.model.services;

import ua.rd.project4.domain.CarRequest;

import java.util.List;

public interface CarRequestService extends EntityService<CarRequest> {
    List<CarRequest> findCarRequestsByClientId(int clientId);
    List<CarRequest> findCarRequestsByCarId(int carId);
    List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);
}
