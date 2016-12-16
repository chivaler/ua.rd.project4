package ua.rd.project4.controller.util;

public interface ViewJsp {
    interface General {
        String LOGIN_PAGE = "jsp/login.jsp";
        String LOGOFF_PAGE = "jsp/logoff.jsp";
        String REGISTER_JSP = "jsp/register.jsp";
        String ERROR_404 = "404.html";
    }

    interface CarsCrud {
        String LIST_CARS_JSP = "/jsp/cars.jsp";
        String EDIT_CAR_JSP = "jsp/car.jsp";
    }

    interface CarRequestsCrud {
        String LIST_CARREQUESTS_JSP = "jsp/car_requests.jsp";
        String EDIT_CARREQUEST_JSP = "jsp/car_request.jsp";
    }

    interface CarFlowsCrud {
        String LIST_CARFLOW_JSP = "jsp/car_flows.jsp";
        String EDIT_CARFLOW_JSP = "jsp/car_flow.jsp";
    }

    interface UsersCrud {
        String LIST_USERS_JSP = "jsp/users.jsp";
        String EDIT_USER_JSP = "jsp/user.jsp";
    }

    interface ClientsCrud {
        String LIST_CLIENTS_JSP = "jsp/clients.jsp";
        String EDIT_CLIENT_JSP = "jsp/client.jsp";
    }

    interface InvoicesCrud {
        String LIST_INVOICES_JSP = "jsp/invoices.jsp";
        String EDIT_INVOICE_JSP = "jsp/invoice.jsp";
        String VIEW_INVOICE_JSP = "jsp/print/invoiceView.jsp";
    }

    interface AdminSpace {
        String ADMIN_JSP = "jsp/admin.jsp";
    }

    interface UserSpace {
        String USER_JSP = "jsp/userspace.jsp";
    }




}
