package ua.rd.project4.Dao;

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

    public EntityDao getGenericDao(Class c){
        switch (c.getName()) {
            case "ua.rd.project4.entities.Car": return getCarDao();
            case "ua.rd.project4.entities.Client": return getClientDao();
            case "ua.rd.project4.entities.SystemUser": return getUserDao();
            case "ua.rd.project4.entities.Invoice": return getInvoiceDao();
            case "ua.rd.project4.entities.CarRequest": return getCarRequestDao();
            default: return getCarFlowDao();
        }
    }
}

