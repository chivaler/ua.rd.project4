package ua.rd.project4.services;

public interface AbstractServiceFactory {
    ClientService getClientService();
    UserService getUserService();
    CarService getCarService();
    CarFlowService getCarFlowService();
    CarRequestService getCarRequestService();
    InvoiceService getInvoiceService();
}
