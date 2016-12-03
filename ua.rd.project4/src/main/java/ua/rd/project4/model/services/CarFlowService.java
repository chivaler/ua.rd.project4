package ua.rd.project4.model.services;

import ua.rd.project4.domain.CarFlow;

import java.util.List;

public interface CarFlowService extends EntityService<CarFlow> {
    List<CarFlow> findCarFlowsByCarId(int carId);

    List<CarFlow> findCarFlowsByCarRequestId(int carRequestId);

    List<CarFlow> findCarFlowsByUserId(int userId);

    List<CarFlow> findCarFlowsByInvoiceId(int invoiceId);

    default boolean isCarInBox(int carId) {
        return findCarFlowsByCarId(carId).stream()
                .mapToInt(s -> s.getCarFlowType()
                        .getValue()).sum() > 0;
    }
}