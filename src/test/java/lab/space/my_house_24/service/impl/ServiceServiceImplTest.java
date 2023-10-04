package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.entity.ServiceBill;
import lab.space.my_house_24.entity.Unit;
import lab.space.my_house_24.model.service.ServiceRequest;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import lab.space.my_house_24.repository.ServiceRepository;
import lab.space.my_house_24.service.UnitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private UnitService unitService;

    @Mock
    private MessageSource message;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    @Test
    void getServiceById() {
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(Service.builder().build()));
        assertEquals(Service.builder().build(), serviceService.getServiceById(1L));
    }

    @Test
    void getAllServicesDto() {
        when(serviceRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(
                List.of(
                        Service.builder()
                                .id(1L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(2L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(3L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(4L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build()
                )
        );
        serviceService.getAllServicesDto();
        verify(serviceRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void getAllServicesByIsActiveDto() {
        when(serviceRepository.findAllByIsActiveOrderById(true)).thenReturn(
                List.of(
                        Service.builder()
                                .id(1L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(2L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(3L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(4L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build()
                )
        );
        serviceService.getAllServicesByIsActiveDto();
        verify(serviceRepository, times(1)).findAllByIsActiveOrderById(true);
    }

    @Test
    void getAllService() {
        when(serviceRepository.findAll()).thenReturn(
                List.of(
                        Service.builder()
                                .id(1L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(2L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(3L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(4L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build()
                )
        );
        serviceService.getAllService();
        verify(serviceRepository, times(1)).findAll();
    }

    @Test
    void saveService() {
        serviceService.saveService(Service.builder().build());
        verify(serviceRepository, times(1)).save(any());
    }

    @Test
    void saveServiceByRequest() {
        ServiceSaveRequest request = ServiceSaveRequest.builder().serviceRequestList(List.of(
                ServiceRequest.builder().id(1L).name("Test1").unitId(1L).build(),
                ServiceRequest.builder().name("Test2").unitId(1L).build()
        )).build();
        when(unitService.getUnitById(anyLong())).thenReturn(Unit.builder().build());
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(Service.builder().id(1L).build()));

        serviceService.saveServiceByRequest(request);

        verify(serviceRepository, times(2)).save(any());
    }

    @Test
    void deleteServiceById() {
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(Service.builder().serviceBillList(List.of()).meterReadingList(List.of()).build()));
        serviceService.deleteServiceById(1L);
        verify(serviceRepository, times(1)).findById(anyLong());
        verify(serviceRepository, times(1)).delete((Service) any());
    }

    @Test
    void deleteServiceByIdBadRequest() {
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(Service.builder().serviceBillList(List.of(ServiceBill.builder().build())).meterReadingList(List.of()).build()));
        assertThrows(IllegalArgumentException.class, () -> {
            serviceService.deleteServiceById(1L);
        });
        verify(serviceRepository, times(1)).findById(anyLong());
        verify(serviceRepository, times(0)).delete((Service) any());
    }

    @Test
    void serviceResponseForSelect() {
        when(serviceRepository.findAll((Specification<Service>) any(), any(PageRequest.class))).thenReturn(
                new PageImpl<>(List.of(
                        Service.builder()
                                .id(1L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(2L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(3L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(4L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build()
                )
                )
        );
        serviceService.serviceResponseForSelect(1, "");
        verify(serviceRepository, times(1)).findAll((Specification<Service>) any(), any(PageRequest.class));
    }

    @Test
    void serviceListForTable() {
        when(serviceRepository.findAll()).thenReturn(
                List.of(
                        Service.builder()
                                .id(1L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(2L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(3L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build(),
                        Service.builder()
                                .id(4L)
                                .isActive(true)
                                .name("Test")
                                .unit(Unit.builder().id(1L).name("Test").build())
                                .build()
                )
        );
        serviceService.serviceListForTable();
        verify(serviceRepository, times(1)).findAll();
    }
}