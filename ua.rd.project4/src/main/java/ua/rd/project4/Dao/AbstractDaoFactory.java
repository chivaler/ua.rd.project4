package ua.rd.project4.Dao;

import ua.rd.project4.entities.*;

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

abstract class CarDao implements EntityDao<Car> {
}

abstract class ClientDao implements EntityDao<Client> {
}

abstract class UserDao implements EntityDao<SystemUser> {
}

abstract class InvoiceDao implements EntityDao<Invoice> {
}

abstract class CarRequestDao implements EntityDao<CarRequest> {
}

abstract class CarFlowDao implements EntityDao<CarFlow> {
}

