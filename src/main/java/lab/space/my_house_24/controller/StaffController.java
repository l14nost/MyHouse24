package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.*;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.StaffValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final StaffValidator staffValidator;

    @GetMapping({"/", ""})
    public String showStaff() {
        return "admin/pages/staff/staff";
    }

    @GetMapping("/card-{id}")
    public String showStaffCardById(@PathVariable("id") Long id) {
        return "admin/pages/staff/staff-card";
    }

    @GetMapping("/{id}")
    public String showStaffEdit(@PathVariable("id") Long id) {
        return "admin/pages/staff/staff-save";
    }

    @GetMapping("/add")
    public String showStaffSave() {
        return "admin/pages/staff/staff-save";
    }


    @PostMapping("/get-all-staff")
    public ResponseEntity<Page<StaffResponse>> getAllStaff(@RequestBody StaffRequest staffRequest) {
        return ResponseEntity.ok(staffService.getAllStaffDto(staffRequest));
    }

    @GetMapping("/get-all-job-title")
    public ResponseEntity<List<EnumResponse>> getAllJobTitle() {
        return ResponseEntity.ok(staffService.getAllJobTitle());
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<List<EnumResponse>> getAllStatus() {
        return ResponseEntity.ok(staffService.getAllStatus());
    }

    @PostMapping("/send-invite")
    public ResponseEntity<?> sendInvite(@RequestBody InviteRequest inviteRequest) {
        staffService.sendInvite(inviteRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-staff-edit-dto/{id}")
    public ResponseEntity<?> getStaffEditDtoById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        return ResponseEntity.ok(staffService.getStaffByIdWithEditDto(id));
    }

    @GetMapping("/get-staff-card-dto/{id}")
    public ResponseEntity<?> getStaffDtoById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        return ResponseEntity.ok(staffService.getStaffByIdWithCardDto(id));
    }

    @PostMapping("/save-staff")
    public ResponseEntity<?> saveStaff(@Valid @RequestBody StaffSaveRequest staffSaveRequest,
                                       BindingResult bindingResult) {
        staffValidator.isEmailUniqueValidation(staffSaveRequest.email(), bindingResult,
                "StaffSaveRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        staffService.saveStaff(staffSaveRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-staff")
    public ResponseEntity<?> updateStaffById(@Valid @RequestBody StaffUpdateRequest staffUpdateRequest,
                                             BindingResult bindingResult) {
        staffValidator.isEmailUniqueValidationWithId(staffUpdateRequest.id(),
                staffUpdateRequest.email(), bindingResult,
                "StaffUpdateRequest", LocaleContextHolder.getLocale());
        staffValidator.isStaffMainDirectorValidation(
                staffUpdateRequest, bindingResult,
                "StaffUpdateRequest", LocaleContextHolder.getLocale()
        );
        if (nonNull(staffUpdateRequest.password()) && !staffUpdateRequest.password().equals("")) {
            staffValidator.passwordEqualsValidation(
                    staffUpdateRequest.password(),
                    staffUpdateRequest.confirmPassword(), bindingResult,
                    "StaffUpdateRequest", LocaleContextHolder.getLocale()
            );
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return staffService.updateStaff(staffUpdateRequest);
    }

    @DeleteMapping("/delete-staff/{id}")
    public ResponseEntity<?> deleteStaffById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }

        return staffService.deleteStaff(id);
    }
    @GetMapping("/get-all-staff-for-house")
    @ResponseBody
    public List<StaffResponseForHouseAdd> getAllStaffForHousePage() {
        return staffService.getAllStaffDtoForHouse();
    }

    @GetMapping("/get-role-by-staff/{id}")
    public ResponseEntity getRoleByStaff(@PathVariable Long id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok().body(staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale()));
    }

}
