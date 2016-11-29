package ua.rd.project4.model.dao;

import ua.rd.project4.domain.CarRequest;

import java.util.List;

public interface CarRequestDao extends EntityDao<CarRequest> {
    List<CarRequest> findCarRequestsByClientId(int clientId);
    List<CarRequest> findCarRequestsByCarId(int carId);
    List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);
}
