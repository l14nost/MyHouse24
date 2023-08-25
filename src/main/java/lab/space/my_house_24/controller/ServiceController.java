package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.service.ServiceRequest;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import lab.space.my_house_24.model.unit.UnitRequest;
import lab.space.my_house_24.model.unit.UnitResponse;
import lab.space.my_house_24.model.unit.UnitSaveRequest;
import lab.space.my_house_24.service.ServiceService;
import lab.space.my_house_24.service.UnitService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("system-service")
@RequiredArgsConstructor
@Slf4j
public class ServiceController {

    private final UnitService unitService;
    private final ServiceService serviceService;
    private final ServiceValidator serviceValidator;

    @GetMapping({"/", ""})
    public String showServicesPage() {
        return "admin/pages/system-service/service";
    }

    @GetMapping("/get-all-services")
    public ResponseEntity<List<ServiceResponse>> getAllServiceDto() {
        return ResponseEntity.ok(serviceService.getAllServicesDto());
    }

    @PostMapping("/save-services")
    public ResponseEntity<?> saveListService(@Valid @RequestBody ServiceSaveRequest serviceSaveRequest,
                                             BindingResult bindingResult) {
        int i = 0;
        for (ServiceRequest serviceRequest : serviceSaveRequest.serviceRequestList()) {
            if (nonNull(serviceRequest.id())) {
                serviceValidator.isNameUniqueValidationWithIdService(i, serviceRequest.id(), serviceRequest.name(),
                        bindingResult, "ServiceSaveRequest", LocaleContextHolder.getLocale());
            } else {
                serviceValidator.isNameUniqueValidationService(i, serviceRequest.name(),
                        bindingResult, "ServiceSaveRequest", LocaleContextHolder.getLocale());
            }
            i++;
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        return serviceService.saveServiceByRequest(serviceSaveRequest);
    }

    @DeleteMapping("/delete-service/{id}")
    public ResponseEntity<?> deleteServiceById(@PathVariable Long id) {
        return serviceService.deleteServiceById(id);
    }

    @GetMapping("/get-all-unit")
    public ResponseEntity<List<UnitResponse>> getAllUnitDto() {
        return ResponseEntity.ok(unitService.getAllUnitDto());
    }

    @PostMapping("/save-unit")
    public ResponseEntity<?> saveListUnit(@Valid @RequestBody UnitSaveRequest unitSaveRequest,
                                          BindingResult bindingResult) {
        int i = 0;
        for (UnitRequest unitRequest : unitSaveRequest.unitRequestList()) {
            if (nonNull(unitRequest.id())) {
                serviceValidator.isNameUniqueValidationWithIdUnit(i, unitRequest.id(), unitRequest.name(),
                        bindingResult, "UnitSaveRequest", LocaleContextHolder.getLocale());
            } else {
                serviceValidator.isNameUniqueValidationUnit(i, unitRequest.name(),
                        bindingResult, "UnitSaveRequest", LocaleContextHolder.getLocale());
            }
            i++;
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return unitService.saveUnitByRequest(unitSaveRequest);
    }

    @DeleteMapping("/delete-unit/{id}")
    public ResponseEntity<?> deleteUnitById(@PathVariable Long id) {
        return unitService.deleteUnitById(id);
    }

    @GetMapping("/get-services-for-select")
    @ResponseBody
    public Page<ServiceResponseForSelect> serviceForSelect(@RequestParam Integer page, @RequestParam String search){
        return serviceService.serviceResponseForSelect(page,search);
    }

}
