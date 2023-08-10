package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.model.service.ServiceRequest;
import lab.space.my_house_24.model.service.ServiceResponse;

public interface ServiceMapper {
    static ServiceResponse toSimpleDto(Service service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .isActiv(service.getIsActive())
                .unit(UnitMapper.toSimpleDto(service.getUnit()))
                .build();
    }

    static Service toServiceUpdate(ServiceRequest serviceRequest, Service service) {
        return service
                .setName(serviceRequest.name())
                .setIsActive(serviceRequest.isActiv());
    }

    static Service toServiceSave(ServiceRequest serviceRequest) {
        return Service.builder()
                .name(serviceRequest.name())
                .isActive(serviceRequest.isActiv())
                .build();
    }
}
