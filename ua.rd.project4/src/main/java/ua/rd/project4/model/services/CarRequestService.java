package ua.rd.project4.model.services;

import ua.rd.project4.domain.CarRequest;

import java.util.List;

public abstract class CarRequestService extends EntityService<CarRequest> {
    public abstract List<CarRequest> findCarRequestsByClientId(int clientId);
    public abstract List<CarRequest> findCarRequestsByCarId(int carId);
    public abstract List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);
}
