package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.entity.ServiceBill;
import lab.space.my_house_24.mapper.ServiceBillMapper;
import lab.space.my_house_24.model.service_bill.ServiceBillRequest;
import lab.space.my_house_24.model.service_bill.ServiceBillResponse;
import lab.space.my_house_24.repository.BillRepository;
import lab.space.my_house_24.repository.ServiceBillRepository;
import lab.space.my_house_24.service.ServiceBillService;
import lab.space.my_house_24.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceBillServiceImpl implements ServiceBillService {
    private final ServiceBillRepository serviceBillRepository;
    private final ServiceService serviceService;
    private final BillRepository billRepository;

    @Override
    public ServiceBill getServiceBillById(Long id) throws EntityNotFoundException {
        log.info("Try to find ServiceBill");
        return serviceBillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceBill not found by id " + id));
    }

    @Override
    public List<ServiceBillResponse> getServiceBillResponsesByBillId(Long billId) throws EntityNotFoundException {
        log.info("Try to convert All ServiceBill by Bill id to Response");
        return getAllServiceBillByBillId(billId)
                .stream()
                .map(ServiceBillMapper::toServiceBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceBill> getAllServiceBillByBillId(Long billId) {
        log.info("Try to get All ServiceBill by Bill id");
        return serviceBillRepository.getAllByBillId(billId);
    }

    @Override
    public void saveServiceBillByRequest(List<ServiceBillRequest> request, Bill bill) throws EntityNotFoundException {
        log.info("Try to save ServiceBill by Request");
        List<ServiceBill> dbServiceBills = getAllServiceBillByBillId(bill.getId());
        if (request.stream().filter(serviceBill -> nonNull(serviceBill.id())).toList().isEmpty() && !dbServiceBills.isEmpty()) {
            deleteListServiceBills(dbServiceBills);
        }
        for (ServiceBillRequest serviceBillRequest : request) {
            if (nonNull(serviceBillRequest.id())) {
                saveServiceBill(
                        ServiceBillMapper.toServiceBill(
                                serviceBillRequest,
                                getServiceBillById(serviceBillRequest.id()),
                                serviceService.getServiceById(serviceBillRequest.serviceId())
                        )
                );
            } else {
                saveServiceBill(
                        ServiceBillMapper.toServiceBill(
                                serviceBillRequest,
                                serviceService.getServiceById(serviceBillRequest.serviceId()),
                                bill
                        )
                );
            }
        }

        log.info("Success save ServiceBill by Request");
    }

    @Override
    public void saveServiceBill(ServiceBill serviceBill) {
        log.info("Try to save ServiceBill");
        serviceBillRepository.save(serviceBill);
        log.info("Success save ServiceBill");
    }

    @Override
    public void deleteServiceBillById(Long id) throws EntityNotFoundException {
        log.info("Try to delete ServiceBill");
        ServiceBill serviceBill = getServiceBillById(id);
        minusTotalPriceFromBill(serviceBill.getBill().getId(), serviceBill.getTotalPrice());
        serviceBillRepository.delete(serviceBill);
        log.info("Success delete ServiceBill");
    }

    @Override
    public void deleteListServiceBills(List<ServiceBill> serviceBills) {
        log.info("Try to delete ServiceBillList");
        serviceBillRepository.deleteAll(serviceBills);
        log.info("Success delete ServiceBillList");
    }

    private void minusTotalPriceFromBill(Long id, BigDecimal price) {
        log.info("Try to find Bill");
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found by id " + id));
        bill.setTotalPrice(bill.getTotalPrice().subtract(price));
        log.info("Try to save find Bill");
        billRepository.save(bill);
        log.info("Success save Bill");
    }
}
