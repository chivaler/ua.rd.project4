package ua.rd.project4.model.dao;

import ua.rd.project4.domain.Car;

import java.sql.Date;
import java.util.List;

public interface CarDao extends EntityDao<Car> {
    List<Car> getCarsInBox();

    List<Car> getCarsOutOfBox();

    List<Car> findAvailableCars(Date dateFrom, Date dateTo);
}
