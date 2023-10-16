package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.model.apartment.ApartmentMastersApplicationRequest;
import lab.space.my_house_24.model.apartment.ApartmentResponseForMastersApplication;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showMastersApplicationPage() {
        return new ModelAndView("admin/pages/masters_application/masters-application");
    }

    @GetMapping("/card-{id}")
    public ModelAndView showMastersApplicationCardById(@PathVariable("id") Long id) {
        return new ModelAndView("admin/pages/masters_application/masters-application-card");
    }

    @GetMapping("/add")
    public ModelAndView showMastersApplicationSavePage() {
        return new ModelAndView("admin/pages/masters_application/masters-application-save");
    }

    @GetMapping("/update-{id}")
    public ModelAndView showMastersApplicationUpdatePage(@PathVariable Long id) {
        return new ModelAndView("admin/pages/masters_application/masters-application-save");
    }

    @GetMapping("/get-masters-application-{id}")
    public ResponseEntity<?> getMastersApplicationById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        try {
            return ResponseEntity.ok(mastersApplicationService.getMastersApplicationResponseById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/get-all-master-call")
    public ResponseEntity<?> getAllMastersApplication(@Valid @RequestBody MastersApplicationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        return ResponseEntity.ok(mastersApplicationService.getAllMastersApplication(request));
    }

    @GetMapping("/get-all-type-master")
    public ResponseEntity<List<EnumResponse>> getAllTypeMaster() {
        return ResponseEntity.ok(mastersApplicationService.getAllTypeMaster());
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<List<EnumResponse>> getAllStatus() {
        return ResponseEntity.ok(mastersApplicationService.getAllStatus());
    }

    @GetMapping("/get-all-staff")
    public ResponseEntity<Page<StaffResponse>> getAllStaff(@RequestParam(required = false) Integer pageIndex,
                                                           @RequestParam(required = false) String staffQuery,
                                                           @RequestParam(required = false) JobTitle role) {
        return ResponseEntity.ok(staffService.getAllStaffMaster(StaffMasterRequest.builder().staffQuery(staffQuery).pageIndex(pageIndex).role(role).build()));
    }

    @GetMapping("/get-all-apartment")
    public ResponseEntity<Page<ApartmentResponseForMastersApplication>> getAllApartments(
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) String apartmentQuery,
            @RequestParam(required = false) Long id) {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponseByUserId(ApartmentMastersApplicationRequest.builder().id(id).apartmentQuery(apartmentQuery).pageIndex(pageIndex).build()));
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<Page<UserResponseForMastersApplication>> getAllUser(@RequestParam(required = false) Integer pageIndex,
                                                                              @RequestParam(required = false) String search) {
        return ResponseEntity.ok(userService.getAllUsersForMastersApplication(pageIndex, search));
    }

    @PostMapping("/save-master-call")
    public ResponseEntity<?> saveMastersApplication(@Valid @RequestBody MastersApplicationSaveRequest request,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            mastersApplicationService.saveMastersApplicationByRequest(request);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PutMapping("/update-master-call")
    public ResponseEntity<?> updateMastersApplicationById(@Valid @RequestBody MastersApplicationUpdateRequest request,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            mastersApplicationService.updateMastersApplicationByRequest(request);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @DeleteMapping("/delete-master-call/{id}")
    public ResponseEntity<?> deleteMastersApplicationById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        try {
            mastersApplicationService.deleteMastersApplicationById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
