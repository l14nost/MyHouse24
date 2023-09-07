package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.entity.ServiceBill;
import lab.space.my_house_24.model.service_bill.ServiceBillRequest;
import lab.space.my_house_24.model.service_bill.ServiceBillResponse;

import java.util.List;
import java.util.stream.Collectors;

public interface ServiceBillMapper {

    static List<ServiceBillResponse> toServiceBillResponseList(List<ServiceBill> serviceBill) {
        return serviceBill
                .stream()
                .map(ServiceBillMapper::toServiceBillResponse)
                .collect(Collectors.toList());
    }

    static ServiceBillResponse toServiceBillResponse(ServiceBill serviceBill) {
        return ServiceBillResponse.builder()
                .id(serviceBill.getId())
                .count(serviceBill.getCount())
                .price(serviceBill.getPrice())
                .totalPrice(serviceBill.getTotalPrice())
                .service(ServiceMapper.toSimpleDto(serviceBill.getService()))
                .build();
    }


    static ServiceBill toServiceBill(ServiceBillRequest request, Service service, Bill bill) {
        return ServiceBill.builder()
                .count(request.count())
                .price(request.price())
                .totalPrice(request.totalPrice())
                .service(service)
                .bill(bill)
                .build();
    }

    static ServiceBill toServiceBill(ServiceBillRequest request, ServiceBill serviceBill, Service service) {
        return serviceBill
                .setCount(request.count())
                .setPrice(request.price())
                .setTotalPrice(request.totalPrice())
                .setService(service);
    }
}
