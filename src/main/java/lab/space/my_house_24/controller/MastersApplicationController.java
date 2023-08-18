package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.apartment.ApartmentMastersApplicationRequest;
import lab.space.my_house_24.model.apartment.ApartmentResponseForMastersApplication;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationSaveRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationUpdateRequest;
import lab.space.my_house_24.model.staff.StaffMasterRequest;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.user.UserResponseForMastersApplication;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.MastersApplicationService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("master-call")
@RequiredArgsConstructor
public class MastersApplicationController {

    private final MastersApplicationService mastersApplicationService;
    private final StaffService staffService;
    private final ApartmentService apartmentService;
    private final UserService userService;

    @GetMapping({"/", ""})
    public String showMastersApplicationPage() {
        return "admin/pages/masters_application/masters-application";
    }

    @GetMapping("/card-{id}")
    public String showMastersApplicationCardById(@PathVariable("id") Long id) {
        return "admin/pages/masters_application/masters-application-card";
    }

    @GetMapping("/add")
    public String showMastersApplicationSavePage() {
        return "admin/pages/masters_application/masters-application-save";
    }

    @GetMapping("/update-{id}")
    public String showMastersApplicationUpdatePage(@PathVariable Long id) {
        return "admin/pages/masters_application/masters-application-save";
    }

    @GetMapping("/get-masters-application-{id}")
    public ResponseEntity<?> getMastersApplicationById(@PathVariable Long id) {
        return mastersApplicationService.getMastersApplicationResponseById(id);
    }

    @GetMapping("/get-all-type-master")
    public ResponseEntity<List<EnumResponse>> getAllTypeMaster() {
        return ResponseEntity.ok(mastersApplicationService.getAllTypeMaster());
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<List<EnumResponse>> getAllStatus() {
        return ResponseEntity.ok(mastersApplicationService.getAllStatus());
    }

    @PostMapping("/get-all-staff")
    public ResponseEntity<List<StaffResponse>> getAllStaff(@RequestBody StaffMasterRequest request) {
        return ResponseEntity.ok(staffService.getAllStaffMaster(request));
    }

    @PostMapping("/get-all-apartment")
    public ResponseEntity<List<ApartmentResponseForMastersApplication>> getAllApartments(
            @RequestBody ApartmentMastersApplicationRequest request) {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByUserId(request));
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<List<UserResponseForMastersApplication>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsersForMastersApplication());
    }

    @PostMapping("/save-master-call")
    public ResponseEntity<?> saveMastersApplication(@Valid @RequestBody MastersApplicationSaveRequest request,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return mastersApplicationService.saveMastersApplicationByRequest(request);
    }

    @PutMapping("/update-master-call")
    public ResponseEntity<?> updateMastersApplicationById(@Valid @RequestBody MastersApplicationUpdateRequest request,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return mastersApplicationService.updateMastersApplicationByRequest(request);
    }
}
