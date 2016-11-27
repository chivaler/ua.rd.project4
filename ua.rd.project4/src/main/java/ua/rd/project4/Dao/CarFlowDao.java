package ua.rd.project4.Dao;

import ua.rd.project4.entities.CarFlow;

import java.util.ArrayList;
import java.util.List;

public abstract class CarFlowDao implements EntityDao<CarFlow> {
    public abstract List<CarFlow> findCarFlowsByClientId(int clientId);
    public abstract List<CarFlow> findCarFlowsByCarId(int carId);
    public abstract List<CarFlow> findCarFlowsByCarRequestId(int carRequestId);
    public abstract List<CarFlow> findCarFlowsByUserId(int userId);
    public abstract List<CarFlow> findCarFlowsByInvoiceId(int invoiceId);

}
