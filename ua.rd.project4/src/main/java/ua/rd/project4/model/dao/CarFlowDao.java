package ua.rd.project4.model.dao;

import ua.rd.project4.domain.CarFlow;

import java.util.List;

public interface CarFlowDao extends EntityDao<CarFlow> {
    List<CarFlow> findCarFlowsByCarId(int carId);
    List<CarFlow> findCarFlowsByCarRequestId(int carRequestId);
    List<CarFlow> findCarFlowsByUserId(int userId);
    List<CarFlow> findCarFlowsByInvoiceId(int invoiceId);

}
