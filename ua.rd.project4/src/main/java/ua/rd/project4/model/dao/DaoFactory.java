package ua.rd.project4.model.dao;

import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

public abstract class DaoFactory {

    public abstract CarDao getCarDao();

    public abstract ClientDao getClientDao();

    public abstract UserDao getUserDao();

    public abstract InvoiceDao getInvoiceDao();

    public abstract CarRequestDao getCarRequestDao();

    public abstract CarFlowDao getCarFlowDao();


}

