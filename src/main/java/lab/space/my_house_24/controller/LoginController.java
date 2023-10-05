package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.staff.ForgotPassEmailRequest;
import lab.space.my_house_24.model.staff.ForgotPassRequest;
import lab.space.my_house_24.service.JwtService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.StaffValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("login")
@RequiredArgsConstructor
public class LoginController {

    private final StaffService staffService;
    private final StaffValidator staffValidator;
    private final JwtService jwtService;

    @GetMapping({"/", ""})
    public ModelAndView showLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            return new ModelAndView("redirect:/statistics");
        } else {
            return new ModelAndView("admin/pages/login/login");
        }
    }

    @GetMapping("/forgot-password")
    public ModelAndView showForgotPasswordPage() {
        return new ModelAndView("admin/pages/login/forgot-password");
    }

    @GetMapping("/forgot-password/{token}")
    public ModelAndView showForgotPasswordChangePage(@PathVariable String token) {
        UserDetails userDetails = staffService.loadUserByToken(token);
        if (!jwtService.isTokenValid(
                token,
                userDetails,
                staffService.getStaffByEmail(userDetails.getUsername()),
                "forgot"
        )) {
            return new ModelAndView("admin/pages/staff/staff-activate-error");
        } else {
            return new ModelAndView("admin/pages/staff/staff-activate");
        }
    }

    @PostMapping("/send-forgot-password-staff")
    public ResponseEntity<?> sendForgotPasswordStaff(@Valid @RequestBody ForgotPassEmailRequest request,
                                                     BindingResult bindingResult) {
        staffValidator.isEmailExistValidation(request.email(),
                bindingResult, "ForgotPassEmailRequest", LocaleContextHolder.getLocale());
        staffValidator.isStaffStatusValidation(request.email(),
                bindingResult, "ForgotPassEmailRequest", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        staffService.sendForgotPasswordUrl(request.email());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/forgot-password-staff-change")
    public ResponseEntity<?> forgotPasswordStaff(@Valid @RequestBody ForgotPassRequest request,
                                                 BindingResult bindingResult) {
        staffValidator.passwordEqualsValidation(
                request.password(),
                request.confirmPassword(), bindingResult,
                "ForgotPassRequest", LocaleContextHolder.getLocale()
        );
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            staffService.forgotPasswordStaff(request);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
