package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.staff.InviteRequest;
import lab.space.my_house_24.service.JwtService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.StaffValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final StaffService staffService;
    private final StaffValidator staffValidator;
    private final JwtService jwtService;

    @GetMapping("/activate-staff/{token}")
    public String showActivateStaffPage(@PathVariable String token) {
        if (!jwtService.isTokenValid(
                token,
                staffService.loadUserByToken(token),
                staffService.getStaffByEmail(staffService.loadUserByToken(token).getUsername())
        )
        ) {
            return "/admin/pages/staff/staff-activate-error";
        } else {
            return "/admin/pages/staff/staff-activate";
        }
    }

    @PutMapping("/update-staff")
    public ResponseEntity<?> activateStaff(@Valid @RequestBody InviteRequest inviteRequest,
                                           BindingResult bindingResult) {
        staffValidator.isStaffActivateValidation(
                inviteRequest, bindingResult,
                "InviteRequest", LocaleContextHolder.getLocale()
        );
        staffValidator.passwordEqualsValidation(
                inviteRequest.password(),
                inviteRequest.confirmPassword(), bindingResult,
                "InviteRequest", LocaleContextHolder.getLocale()
        );
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return staffService.activateStaff(inviteRequest);
    }
}
