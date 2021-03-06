package ua.rd.project4.model.holders;

import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.dao.DaoFactory;

import java.math.BigDecimal;
import java.sql.Date;

public class CarRequestHolder extends CarRequest  {
    private final DaoFactory daoFactory;
    private int carId;
    private int clientId;
    private int invoiceId;

    public CarRequestHolder(int carId,
                            int clientId,
                            Date dateFrom, Date dateTo,
                            BigDecimal totalCost,
                            CarRequest.RequestStatus status,
                            int invoiceId,
                            String rejectReason,
                            DaoFactory daoFactory) {
        super(null, null, dateFrom, dateTo, totalCost, status, null,rejectReason);
        this.carId = carId;
        this.clientId = clientId;
        this.invoiceId = invoiceId;
        this.daoFactory = daoFactory;
    }

    @Override
    public int getCarId() {
        return carId;
    }

    @Override
    public int getClientId() {
        return clientId;
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
    public Client getClient() {
        return (clientId > 0)? daoFactory.getClientDao().getById(clientId) : null;
    }

    @Override
    public void setClient(Client client) {
        this.clientId = (client == null) ? 0 : client.getId();
    }

    @Override
    public Invoice getInvoice() {
        return (invoiceId > 0)? daoFactory.getInvoiceDao().getById(invoiceId) : null;
    }

    @Override
    public void setInvoice(Invoice invoice) {
        this.invoiceId = (invoice == null) ? 0 : invoice.getId();
    }
}
