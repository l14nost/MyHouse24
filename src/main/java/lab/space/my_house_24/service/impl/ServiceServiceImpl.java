package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.mapper.ServiceMapper;
import lab.space.my_house_24.model.service.ServiceRequest;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import lab.space.my_house_24.repository.ServiceRepository;
import lab.space.my_house_24.service.ServiceService;
import lab.space.my_house_24.service.UnitService;
import lab.space.my_house_24.specification.ServiceSpecificationForSelect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final UnitService unitService;
    @Override
    public lab.space.my_house_24.entity.Service getServiceById(Long id) throws EntityNotFoundException{
        log.info("Try to search Service by id " + id);
        return serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found by id " + id));
    }

    @Override
    public List<ServiceResponse> getAllServicesDto() {
        log.info("Try to get All Service and convert in Dto");
        return serviceRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))
                .stream()
                .map(ServiceMapper::toSimpleDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceResponse> getAllServicesByIsActiveDto() {
        log.info("Try to get All Active Service and convert in Dto");
        return serviceRepository.findAllByIsActiveOrderById(true)
                .stream()
                .map(ServiceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void saveService(lab.space.my_house_24.entity.Service service) {
        log.info("Try to save Service");
        serviceRepository.save(service);
        log.info("Success save Service");
    }

    @Override
    public ResponseEntity<?> saveServiceByRequest(ServiceSaveRequest serviceSaveRequest) {
        try {
            log.info("Try to save Service by Request");
            lab.space.my_house_24.entity.Service service;
            for (ServiceRequest serviceRequest : serviceSaveRequest.serviceRequestList()) {
                if (nonNull(serviceRequest.id())) {
                    service = ServiceMapper.toServiceUpdate(
                                    serviceRequest,
                                    getServiceById(serviceRequest.id()));
                } else {
                    service = ServiceMapper.toServiceSave(serviceRequest);
                }
                service.setUnit(unitService.getUnitById(serviceRequest.unitId()));
                saveService(service);
            }
            log.info("Success save Service by Request");
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e){
            log.error("Error save Service by Request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteServiceById(Long id) {
        try {
            log.info("Try to delete Service");
            lab.space.my_house_24.entity.Service service = getServiceById(id);
            if (service.getServiceBillList().isEmpty() && service.getMeterReadingList().isEmpty()){
                serviceRepository.delete(service);
            }else {
                String error;
                if (LocaleContextHolder.getLocale().toLanguageTag().equals("uk")) {
                    error = "Послугою користуються. Видалення неможливе.";
                } else {
                    error = "The service is used. Removal is not possible.";
                }
                log.warn("Warning delete Service");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
            log.info("Success delete Service");
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error delete Service");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public Page<ServiceResponseForSelect> serviceResponseForSelect(Integer page, String search) {
        ServiceSpecificationForSelect serviceSpecificationForSelect = ServiceSpecificationForSelect.builder().search(search).build();
        return serviceRepository.findAll(serviceSpecificationForSelect, PageRequest.of(page,5)).map(ServiceMapper::entityToDtoForSelect);
    }

    @Override
    public List<ServiceResponseForSelect> serviceListForTable() {
        return serviceRepository.findAll().stream().map(ServiceMapper::entityToDtoForSelect).toList();
    }
}
