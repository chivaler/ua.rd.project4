package ua.rd.project4.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;

class cmdCrudInvoice extends cmdCrudGeneric<Invoice> {
    private static final cmdCrudInvoice instance = new cmdCrudInvoice();
    private static final String LIST_INVOICES_JSP = "jsp/invoices.jsp";
    private static final String EDIT_INVOICE_JSP = "jsp/invoice.jsp";
    private final Logger logger = LogManager.getLogger(cmdCrudInvoice.class);
    private final CarService clientService = getServiceFactory().getCarService();
    private final InvoiceService invoiceService = getServiceFactory().getInvoiceService();


    private cmdCrudInvoice() {
    }

    static cmdCrudInvoice getInstance() {
        return instance;
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    AbstractServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    String getEntityJsp() {
        return EDIT_INVOICE_JSP;
    }

    @Override
    String getEntityListJsp() {
        return LIST_INVOICES_JSP;
    }

    @Override
    EntityService<Invoice> getEntityService() {
        return invoiceService;
    }

    @Override
    Invoice parseToEntity(HttpServletRequest req) throws InvalidParameterException {
//        Car.CarType carType = null;
//        int rentPricePerDay = -1;
//        int price = -1;
//        String model = Optional.ofNullable(req.getParameter("model")).orElse("");
//        String color = Optional.ofNullable(req.getParameter("color")).orElse("");
//        String registrationNumber = Optional.ofNullable(req.getParameter("registrationNumber")).orElse("");
//        String description = Optional.ofNullable(req.getParameter("description")).orElse("");
//        if ("".equals(model))
//            throw new RequiredParameterException("model");
//        if ("".equals(registrationNumber))
//            throw new RequiredParameterException("registrationNumber");
//        try {
//            carType = Car.CarType.valueOf(req.getParameter("carType"));
//        } catch (Exception e) {
//            throw new RequiredParameterException("carType");
//        }
//        try {
//            rentPricePerDay = Integer.valueOf(req.getParameter("rentPricePerDay"));
//        } catch (Exception e) {
//            throw new RequiredParameterException("rentPricePerDay");
//        }
//        try {
//            price = Integer.valueOf(req.getParameter("price"));
//        } catch (Exception e) {
//            throw new RequiredParameterException("price");
//        }
//        if (price < 1)
//            throw new InvalidParameterException("price");
//        if (price < 1)
//            throw new InvalidParameterException("rentPricePerDay");
//        return new Car(model, color, carType, registrationNumber, description, price, rentPricePerDay);
        return null;
    }
}