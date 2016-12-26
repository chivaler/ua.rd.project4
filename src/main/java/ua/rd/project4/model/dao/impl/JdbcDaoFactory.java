package ua.rd.project4.model.dao.impl;

import ua.rd.project4.model.dao.*;

public class JdbcDaoFactory extends DaoFactory {
    private static final JdbcDaoFactory instance = new JdbcDaoFactory();

    private JdbcDaoFactory() {
    }

    public static JdbcDaoFactory getInstance() {
        return instance;
    }

    @Override
    public CarDao getCarDao() {
        return JdbcCarDao.getInstance();
    }

    @Override
    public ClientDao getClientDao() {
        return JdbcClientDao.getInstance();
    }

    @Override
    public UserDao getUserDao() {
        return JdbcUserDao.getInstance();
    }

    @Override
    public InvoiceDao getInvoiceDao() {
        return JdbcInvoiceDao.getInstance();
    }

    @Override
    public CarRequestDao getCarRequestDao() {
        return JdbcCarRequestDao.getInstance();
    }

    @Override
    public CarFlowDao getCarFlowDao() {
        return JdbcCarFlowDao.getInstance();
    }


}