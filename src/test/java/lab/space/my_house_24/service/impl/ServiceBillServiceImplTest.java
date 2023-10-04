package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.entity.ServiceBill;
import lab.space.my_house_24.entity.Unit;
import lab.space.my_house_24.model.service_bill.ServiceBillRequest;
import lab.space.my_house_24.repository.BillRepository;
import lab.space.my_house_24.repository.ServiceBillRepository;
import lab.space.my_house_24.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceBillServiceImplTest {

    @Mock
    private ServiceBillRepository serviceBillRepository;

    @Mock
    private ServiceService serviceService;

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private ServiceBillServiceImpl serviceBillService;

    @Test
    void getServiceBillById() {
        when(serviceBillRepository.findById(anyLong())).thenReturn(Optional.of(ServiceBill.builder().build()));
        assertEquals(ServiceBill.builder().build(), serviceBillService.getServiceBillById(1L));
    }

    @Test
    void getServiceBillResponsesByBillId() {
        List<ServiceBill> serviceBills = List.of(
                ServiceBill.builder()
                        .id(1L)
                        .count(10.1)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L).name("Test")
                                .isActive(false)
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build(),
                ServiceBill.builder()
                        .id(2L)
                        .count(10.1)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L).name("Test")
                                .isActive(false)
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build(),
                ServiceBill.builder()
                        .id(3L)
                        .count(10.1)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L).name("Test")
                                .isActive(false)
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build(),
                ServiceBill.builder()
                        .id(4L)
                        .count(10.1)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L).name("Test")
                                .isActive(false)
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()
        );
        when(serviceBillRepository.getAllByBillId(anyLong())).thenReturn(serviceBills);
        assertEquals(4, serviceBillService.getServiceBillResponsesByBillId(1L).size());
        verify(serviceBillRepository, times(1)).getAllByBillId(anyLong());
    }

    @Test
    void getAllServiceBillByBillId() {
        List<ServiceBill> serviceBills = List.of(
                ServiceBill.builder().build(),
                ServiceBill.builder().build(),
                ServiceBill.builder().build(),
                ServiceBill.builder().build()
        );
        when(serviceBillRepository.getAllByBillId(anyLong())).thenReturn(serviceBills);
        assertEquals(serviceBills, serviceBillService.getAllServiceBillByBillId(1L));
        verify(serviceBillRepository, times(1)).getAllByBillId(anyLong());
    }

    @Test
    void saveServiceBillByRequest() {
        List<ServiceBillRequest> requests = List.of(
                ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ZERO)
                        .count(10.0)
                        .build(),
                ServiceBillRequest.builder()
                        .serviceId(1L)
                        .price(BigDecimal.ZERO)
                        .count(10.0)
                        .build()
        );
        List<ServiceBill> serviceBills = new ArrayList<>();
        Bill billRequest = Bill.builder().id(1L).build();

        when(serviceBillRepository.getAllByBillId(anyLong())).thenReturn(serviceBills);
        when(serviceBillRepository.findById(anyLong())).thenReturn(Optional.of(ServiceBill.builder().build()));
        when(serviceService.getServiceById(anyLong())).thenReturn(Service.builder().build());
        serviceBillService.saveServiceBillByRequest(requests, billRequest);
        verify(serviceBillRepository, times(1)).getAllByBillId(anyLong());
        verify(serviceBillRepository, times(2)).save(any());
        verify(serviceBillRepository, times(0)).deleteAll(any());
        verify(serviceBillRepository, times(1)).findById(anyLong());
        verify(serviceService, times(2)).getServiceById(anyLong());
    }

    @Test
    void saveServiceBillByRequestDeleteDBServiceBills() {
        List<ServiceBillRequest> requests = List.of(
                ServiceBillRequest.builder()
                        .serviceId(1L)
                        .price(BigDecimal.ZERO)
                        .count(10.0)
                        .build(),
                ServiceBillRequest.builder()
                        .serviceId(2L)
                        .price(BigDecimal.ZERO)
                        .count(10.0)
                        .build()
        );
        List<ServiceBill> serviceBills = List.of(ServiceBill.builder().build());
        Bill billRequest = Bill.builder().id(1L).build();

        when(serviceBillRepository.getAllByBillId(anyLong())).thenReturn(serviceBills);
        when(serviceService.getServiceById(anyLong())).thenReturn(Service.builder().build());
        serviceBillService.saveServiceBillByRequest(requests, billRequest);
        verify(serviceBillRepository, times(1)).getAllByBillId(anyLong());
        verify(serviceBillRepository, times(2)).save(any());
        verify(serviceBillRepository, times(1)).deleteAll(any());
        verify(serviceBillRepository, times(0)).findById(anyLong());
        verify(serviceService, times(2)).getServiceById(anyLong());
    }

    @Test
    void saveServiceBill() {
        serviceBillService.saveServiceBill(ServiceBill.builder().build());
        verify(serviceBillRepository, times(1)).save(any());
    }

    @Test
    void deleteServiceBillById() {
        when(serviceBillRepository.findById(anyLong())).thenReturn(Optional.of(ServiceBill.builder().bill(Bill.builder().id(1L).build()).totalPrice(BigDecimal.ZERO).build()));
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(Bill.builder().totalPrice(BigDecimal.ZERO).build()));
        serviceBillService.deleteServiceBillById(1L);
        verify(serviceBillRepository, times(1)).findById(anyLong());
        verify(billRepository, times(1)).findById(anyLong());
        verify(billRepository, times(1)).save(any());
        verify(serviceBillRepository, times(1)).delete(any());
    }

    @Test
    void deleteListServiceBills() {
        serviceBillService.deleteListServiceBills(List.of(ServiceBill.builder().build()));
        verify(serviceBillRepository, times(1)).deleteAll(any());
    }
}