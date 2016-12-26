package ua.rd.project4.model.services;

public interface ServiceFactory {
    ClientService getClientService();
    UserService getUserService();
    CarService getCarService();
    CarFlowService getCarFlowService();
    CarRequestService getCarRequestService();
    InvoiceService getInvoiceService();
}
