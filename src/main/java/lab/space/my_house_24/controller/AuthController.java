package lab.space.my_house_24.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.staff.InviteRequest;
import lab.space.my_house_24.service.JwtService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.StaffValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final StaffService staffService;
    private final StaffValidator staffValidator;
    private final JwtService jwtService;

    @GetMapping("/activate-staff/{token}")
    public ModelAndView showActivateStaffPage(@PathVariable String token) {
        UserDetails userDetails;
        try {
            userDetails = staffService.loadUserByToken(token);
        }catch (JWTDecodeException | EntityNotFoundException e) {
            log.warn(e.getMessage());
            return new ModelAndView("/admin/pages/staff/staff-activate-error");
        }
        if (!jwtService.isTokenValid(
                token,
                userDetails,
                staffService.getStaffByEmail(userDetails.getUsername()),
                "activate"
        )) {
            return new ModelAndView("admin/pages/staff/staff-activate-error");
        } else {
            return new ModelAndView("admin/pages/staff/staff-activate");
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
        try {
            staffService.activateStaff(inviteRequest);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
