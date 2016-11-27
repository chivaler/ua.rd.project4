package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.CarFlow;
import ua.rd.project4.entities.CarRequest;

class JdbcCarRequestSevice extends CarRequestService {
    private static final JdbcCarRequestSevice instance = new JdbcCarRequestSevice();
    private JdbcCarRequestSevice() {
    }

    public static JdbcCarRequestSevice getInstance() {
        return instance;
    }

    @Override
    EntityDao<CarRequest> getDao() {
        return JdbcDaoFactory.getInstance().getCarRequestDao();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }
}
