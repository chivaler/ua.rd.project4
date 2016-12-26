package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.services.*;

public class DefaultServiceFactory implements ServiceFactory {
    private static final DefaultServiceFactory instance = new DefaultServiceFactory();

    private DefaultServiceFactory() {
    }

    public static DefaultServiceFactory getInstance() {
        return instance;
    }

    @Override
    public ClientService getClientService() {
        return DefaultClientService.getInstance();
    }

    @Override
    public UserService getUserService() {
        return DefaultUserSevice.getInstance();
    }

    @Override
    public CarService getCarService() {
        return DefaultJdbcCarSevice.getInstance();
    }

    @Override
    public CarFlowService getCarFlowService() {
        return DefaultCarFlowService.getInstance();
    }

    @Override
    public CarRequestService getCarRequestService() {
        return DefaultCarRequestService.getInstance();
    }

    @Override
    public InvoiceService getInvoiceService() {
        return DefaultInvoiceSevice.getInstance();
    }
}
