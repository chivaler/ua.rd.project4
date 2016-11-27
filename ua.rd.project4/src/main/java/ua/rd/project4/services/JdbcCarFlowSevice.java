package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.CarFlow;

class JdbcCarFlowSevice extends CarFlowService {
    private static final JdbcCarFlowSevice instance = new JdbcCarFlowSevice();
    private JdbcCarFlowSevice() {
    }

    public static JdbcCarFlowSevice getInstance() {
        return instance;
    }

    @Override
    EntityDao<CarFlow> getDao() {
        return JdbcDaoFactory.getInstance().getCarFlowDao();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }
}
