package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import lab.space.my_house_24.model.unit.UnitResponse;
import lab.space.my_house_24.model.unit.UnitSaveRequest;
import lab.space.my_house_24.service.ServiceService;
import lab.space.my_house_24.service.UnitService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("system-service")
@RequiredArgsConstructor
@Slf4j
public class ServiceController {

    private final UnitService unitService;
    private final ServiceService serviceService;

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
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return unitService.saveUnitByRequest(unitSaveRequest);
    }

    @DeleteMapping("/delete-unit/{id}")
    public ResponseEntity<?> deleteUnitById(@PathVariable Long id) {
        return unitService.deleteUnitById(id);
    }

}
