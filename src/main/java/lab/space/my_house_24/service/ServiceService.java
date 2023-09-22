package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceService {
    Service getServiceById(Long id);
    List<ServiceResponse> getAllServicesDto();
    List<ServiceResponse> getAllServicesByIsActiveDto();
    List<Service> getAllService();
    void saveService(Service service);

    ResponseEntity<?> saveServiceByRequest(ServiceSaveRequest serviceSaveRequest);

    ResponseEntity<?> deleteServiceById(Long id);

    Page<ServiceResponseForSelect> serviceResponseForSelect(Integer page, String search);

    List<ServiceResponseForSelect> serviceListForTable();
}
