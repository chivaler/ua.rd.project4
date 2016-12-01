package ua.rd.project4.model.services;

import ua.rd.project4.domain.CarRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public interface CarRequestService extends EntityService<CarRequest> {
    List<CarRequest> findCarRequestsByClientId(int clientId);
    List<CarRequest> findCarRequestsByCarId(int carId);
    List<CarRequest> findCarRequestsByInvoiceId(int invoiceId);

    enum CarRequestStatus{
        POSSIBLE, CONFLICT, IMPOSSIBLE;
    }

    default CarRequestStatus isPossible(int carRequestId) {
        return CarRequestStatus.POSSIBLE;
    }

    default List<Map<String,String>> getCarRequestsWithStatuses() {
        return findAll().stream()
//                .filter(s -> s.isApproved())
                .map(s -> { Map<String,String> map= new HashMap<>();
                    map.put("id", String.valueOf(s.getId()));
                    map.put("approved",String.valueOf(s.isApproved()));
                    map.put("carId",String.valueOf(s.getCarId()));
                    map.put("carStr",String.valueOf(s.getCarId()>0?s.getCar().toString():""));
                    map.put("available",String.valueOf(isPossible(s.getId())));
                    return  map;})
                .collect(Collectors.toList());
    }
}
