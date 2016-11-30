package ua.rd.project4.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.*;

public class JdbcDaoFactory extends AbstractDaoFactory {
    private static final JdbcDaoFactory instance = new JdbcDaoFactory();
    private static Logger logger = LogManager.getLogger(JdbcDaoFactory.class);

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