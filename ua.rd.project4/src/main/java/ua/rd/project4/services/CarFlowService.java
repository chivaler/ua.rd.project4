package ua.rd.project4.services;

import ua.rd.project4.entities.CarFlow;

import java.util.List;

public abstract class CarFlowService extends EntityService<CarFlow> {
    public abstract List<CarFlow> findCarFlowsByClientId(int clientId);
    public abstract List<CarFlow> findCarFlowsByCarId(int carId);
    public abstract List<CarFlow> findCarFlowsByCarRequestId(int carRequestId);
    public abstract List<CarFlow> findCarFlowsByUserId(int userId);
    public abstract List<CarFlow> findCarFlowsByInvoiceId(int invoiceId);
}

//    private Car car;
//    private CarFlowType carFlowType;
//    private Client client;
//    private CarRequest carRequest;
//    private SystemUser responsiblePerson;
//    private Invoice invoice;
//    private String supplement;