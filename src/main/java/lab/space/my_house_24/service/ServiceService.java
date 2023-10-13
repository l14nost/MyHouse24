package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServiceService {
    Service getServiceById(Long id);
    List<ServiceResponse> getAllServicesDto();

    Page<ServiceResponse> getAllServicesByIsActiveDto(Integer pageIndex, String search);

    List<Service> getAllService();
    void saveService(Service service);

    void saveServiceByRequest(ServiceSaveRequest serviceSaveRequest);

    void deleteServiceById(Long id);

    Page<ServiceResponseForSelect> serviceResponseForSelect(Integer page, String search);

    List<ServiceResponseForSelect> serviceListForTable();
}
