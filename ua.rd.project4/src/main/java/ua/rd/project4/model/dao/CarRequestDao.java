package ua.rd.project4.model.dao;

import ua.rd.project4.domain.CarRequest;

import java.util.List;

public abstract class CarRequestDao implements EntityDao<CarRequest> {
    public abstract List<CarRequest> findCarRequestsByClientId(int clientId);
    public abstract List<CarRequest> findCarRequestsByCarId(int carId);
    public abstract List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);
}
