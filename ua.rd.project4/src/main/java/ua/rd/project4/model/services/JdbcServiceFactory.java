package ua.rd.project4.model.services;

public class JdbcServiceFactory implements AbstractServiceFactory {
    private static final JdbcServiceFactory instance = new JdbcServiceFactory();

    private JdbcServiceFactory() {
    }

    public static JdbcServiceFactory getInstance() {
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
        return JdbcCarFlowService.getInstance();
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
