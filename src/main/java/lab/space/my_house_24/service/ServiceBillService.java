package lab.space.my_house_24.service;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.entity.ServiceBill;
import lab.space.my_house_24.model.service_bill.ServiceBillRequest;
import lab.space.my_house_24.model.service_bill.ServiceBillResponse;

import java.util.List;

public interface ServiceBillService {
    ServiceBill getServiceBillById(Long id) throws EntityNotFoundException;

    List<ServiceBillResponse> getServiceBillResponsesByBillId(Long billId) throws EntityNotFoundException;

    List<ServiceBill> getAllServiceBillByBillId(Long billId);

    void saveServiceBillByRequest(List<ServiceBillRequest> request, Bill bill) throws EntityNotFoundException;

    void saveServiceBill(ServiceBill serviceBill);

    void deleteServiceBillById(Long id) throws EntityNotFoundException;

    void deleteListServiceBills(List<ServiceBill> serviceBills);
}
