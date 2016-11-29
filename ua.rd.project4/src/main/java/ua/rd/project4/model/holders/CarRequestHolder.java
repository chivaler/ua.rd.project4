package ua.rd.project4.model.holders;

import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;

import java.sql.Date;

public class CarRequestHolder extends CarRequest implements Holder<CarRequest>  {
    public CarRequestHolder(Car car, Client client, Date dateFrom, Date dateTo, int totalCost, boolean approved, Invoice invoice) {
        super(car, client, dateFrom, dateTo, totalCost, approved, invoice);
    }
}
