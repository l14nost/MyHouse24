package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceService {
    Service getServiceById(Long id);
    List<ServiceResponse> getAllServicesDto();

    void saveService(Service service);

    ResponseEntity<?> saveServiceByRequest(ServiceSaveRequest serviceSaveRequest);

    ResponseEntity<?> deleteServiceById(Long id);
}
