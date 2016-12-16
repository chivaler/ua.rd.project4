package ua.rd.project4.model.holders;

import ua.rd.project4.domain.*;
import ua.rd.project4.model.dao.DaoFactory;

public class CarFlowHolder extends CarFlow {
    private final DaoFactory daoFactory;
    private int carId;
    private int carRequestId;
    private int responsiblePersonId;
    private int invoiceId;

    public CarFlowHolder(int carId,
                         CarFlowType carFlowType,
                         int carRequestId,
                         int responsiblePersonId,
                         int invoiceId,
                         String supplement,
                         DaoFactory daoFactory) {
        super(null, carFlowType, null, null, null, supplement);
        this.carId = carId;
        this.responsiblePersonId = responsiblePersonId;
        this.invoiceId = invoiceId;
        this.carRequestId = carRequestId;
        this.daoFactory = daoFactory;
    }

    @Override
    public int getCarId() {
        return carId;
    }

    @Override
    public int getCarRequestId() {
        return carRequestId;
    }

    @Override
    public int getResponsiblePersonId() {
        return responsiblePersonId;
    }

    @Override
    public int getInvoiceId() {
        return invoiceId;
    }

    @Override
    public Car getCar() {
        return (carId > 0) ? daoFactory.getCarDao().getById(carId) : null;
    }

    @Override
    public void setCar(Car car) {
        this.carId = (car == null) ? 0 : car.getId();
    }

    @Override
    public CarRequest getCarRequest() {
        return (carRequestId > 0) ? daoFactory.getCarRequestDao().getById(carRequestId) : null;
    }

    @Override
    public void setCarRequest(CarRequest carRequest) {
        this.carRequestId = (carRequest == null) ? 0 : carRequest.getId();
    }

    @Override
    public User getResponsiblePerson() {
        return (responsiblePersonId > 0) ? daoFactory.getUserDao().getById(responsiblePersonId) : null;
    }

    @Override
    public void setResponsiblePerson(User responsiblePerson) {
        this.responsiblePersonId = (responsiblePerson == null) ? 0 : responsiblePerson.getId();
    }

    @Override
    public Invoice getInvoice() {
        return (invoiceId > 0) ? daoFactory.getInvoiceDao().getById(invoiceId) : null;
    }

    @Override
    public void setInvoice(Invoice invoice) {
        this.invoiceId = (invoice == null) ? 0 : invoice.getId();
    }
}
