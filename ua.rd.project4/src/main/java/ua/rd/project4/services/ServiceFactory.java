package ua.rd.project4.services;

public class ServiceFactory implements AbstractServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    @Override
    public ClientService getClientService() {
        return JdbcClientSevice.getInstance();
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
        return JdbcCarFlowSevice.getInstance();
    }

    @Override
    public CarRequestService getCarRequestService() {
        return JdbcCarRequestSevice.getInstance();
    }

    @Override
    public InvoiceService getInvoiceService() {
        return JdbcInvoiceSevice.getInstance();
    }
}
