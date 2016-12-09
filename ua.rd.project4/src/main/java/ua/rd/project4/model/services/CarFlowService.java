package ua.rd.project4.model.services;

import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.CarRequestApproveNeededException;
import ua.rd.project4.model.exceptions.CarRequestPaymentNeededException;
import ua.rd.project4.model.exceptions.WrongCarFlowDirectionException;

import java.util.List;

public interface CarFlowService extends EntityService<CarFlow> {
    List<CarFlow> findCarFlowsByCarId(int carId);

    List<CarFlow> findCarFlowsByCarRequestId(int carRequestId);

    List<CarFlow> findCarFlowsByUserId(int userId);

    List<CarFlow> findCarFlowsByInvoiceId(int invoiceId);

    List<CarFlow> findAll(int n);

    CarFlow findLastCarFlowOfCar(int carId);

    boolean checkInCarFlowOut(CarFlow carFlow) throws CarRequestApproveNeededException, CarRequestPaymentNeededException, WrongCarFlowDirectionException;

    void checkInCarFlowIn(int carFlowOutId, User user) throws WrongCarFlowDirectionException;

    boolean isCarInBox(int carId);

    List<Car> getCarsInBox();

    List<Car> getCarsOutOfBox();

}