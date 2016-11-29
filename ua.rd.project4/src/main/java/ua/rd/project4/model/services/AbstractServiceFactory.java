package ua.rd.project4.model.services;

public interface AbstractServiceFactory {
    ClientService getClientService();
    UserService getUserService();
    CarService getCarService();
    CarFlowService getCarFlowService();
    CarRequestService getCarRequestService();
    InvoiceService getInvoiceService();
}
