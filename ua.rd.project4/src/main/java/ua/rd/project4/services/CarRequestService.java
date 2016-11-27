package ua.rd.project4.services;

import ua.rd.project4.entities.CarRequest;

import java.util.List;

public abstract class CarRequestService extends EntityService<CarRequest> {
    public abstract List<CarRequest> findCarRequestsByClientId(int clientId);
    public abstract List<CarRequest> findCarRequestsByCarId(int carId);
    public abstract List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);
}
