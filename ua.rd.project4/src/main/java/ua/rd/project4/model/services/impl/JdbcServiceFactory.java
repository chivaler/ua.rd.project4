package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.services.*;

public class JdbcServiceFactory implements ServiceFactory {
    private static final JdbcServiceFactory instance = new JdbcServiceFactory();

    private JdbcServiceFactory() {
    }

    public static JdbcServiceFactory getInstance() {
        return instance;
    }

    @Override
    public ClientService getClientService() {
        return JdbcClientService.getInstance();
    }

    @Override
    public UserService getUserService() {
        return JdbcUserSevice.getInstance();
    }

    @Override
    public CarService getCarService() {
        return JdbcCarSevice.getInstance();
    }

    @Override
    public CarFlowService getCarFlowService() {
        return JdbcCarFlowService.getInstance();
    }

    @Override
    public CarRequestService getCarRequestService() {
        return JdbcCarRequestService.getInstance();
    }

    @Override
    public InvoiceService getInvoiceService() {
        return JdbcInvoiceSevice.getInstance();
    }
}
