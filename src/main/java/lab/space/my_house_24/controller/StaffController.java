package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.staff.StaffSaveRequest;
import lab.space.my_house_24.model.staff.StaffUpdateRequest;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.StaffValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final StaffValidator staffValidator;

    @GetMapping({"/", ""})
    public String showLogin() {
        return "/admin/pages/staff/staff";
    }

    @GetMapping("/get-all-staff")
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaffDto());
    }

    @GetMapping("/get-staff-simple-dto/{id}")
    public ResponseEntity<?> getStaffSimpleDtoById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        return ResponseEntity.ok(staffService.getStaffByIdWithSimpleDto(id));
    }

    @GetMapping("/get-staff-dto/{id}")
    public ResponseEntity<?> getStaffDtoById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        return ResponseEntity.ok(staffService.getStaffByIdWithDto(id));
    }

    @DeleteMapping("/delete-staff/{id}")
    public ResponseEntity<?> deleteStaffById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }

        return staffService.deleteStaff(id);
    }

    @PutMapping("/save-staff")
    public ResponseEntity<?> saveStaff(@Valid @RequestBody StaffSaveRequest staffSaveRequest,
                                       BindingResult bindingResult) {
        staffValidator.isEmailUniqueValidation(staffSaveRequest.email(), bindingResult);
        staffValidator.isFirstAndLastNameUniqueValidation(staffSaveRequest.firstname(),
                staffSaveRequest.lastname(), bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        staffService.saveStaff(staffSaveRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-staff")
    public ResponseEntity<?> updateStaffById(@Valid @RequestBody StaffUpdateRequest staffUpdateRequest,
                                             BindingResult bindingResult) {
        staffValidator.isEmailUniqueValidationWithId(staffUpdateRequest.id(),
                staffUpdateRequest.email(), bindingResult);
        staffValidator.isFirstAndLastNameUniqueValidationWithId(staffUpdateRequest.id(),
                staffUpdateRequest.firstname(), staffUpdateRequest.lastname(), bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return staffService.updateStaff(staffUpdateRequest);
    }

}
