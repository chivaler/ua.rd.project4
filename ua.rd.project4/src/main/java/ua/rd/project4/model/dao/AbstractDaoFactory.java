package ua.rd.project4.model.dao;

public abstract class AbstractDaoFactory {
    public enum DaoType {
        JDBC
    }

    public abstract CarDao getCarDao();

    public abstract ClientDao getClientDao();

    public abstract UserDao getUserDao();

    public abstract InvoiceDao getInvoiceDao();

    public abstract CarRequestDao getCarRequestDao();

    public abstract CarFlowDao getCarFlowDao();

    public static AbstractDaoFactory getDaoFactory(DaoType type) {
        switch (type) {
            case JDBC:
                return JdbcDaoFactory.getInstance();
            default:
                return null;
        }
    }

}

