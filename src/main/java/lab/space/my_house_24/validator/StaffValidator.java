package lab.space.my_house_24.validator;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.staff.InviteRequest;
import lab.space.my_house_24.model.staff.StaffUpdateRequest;
import lab.space.my_house_24.repository.StaffRepository;
import lab.space.my_house_24.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class StaffValidator {
    private final StaffRepository staffRepository;
    private final StaffService staffService;

    public void isStaffMainDirectorValidation(StaffUpdateRequest staffUpdateRequest, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            Staff director = staffService.getMainDirector();
            String directorResponse;
            if (locale.toLanguageTag().equals("uk")) {
                directorResponse = "Змінювати це поле заборонено";
            } else {
                directorResponse = "Changing this field is not allowed";
            }
            if (director.getId() == staffUpdateRequest.id().longValue()
                    && !director.getRole().getJobTitle().equals(staffUpdateRequest.role())
            ) {
                bindingResult.addError(new FieldError(object, "role", directorResponse));
            }
            if (director.getId() == staffUpdateRequest.id().longValue()
                    && !director.getStaffStatus().equals(staffUpdateRequest.status())
            ) {
                bindingResult.addError(new FieldError(object, "status", directorResponse));
            }
        }

    }

    public void isStaffActivateValidation(InviteRequest inviteRequest, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            Staff staff = staffService.getStaffByEmail(staffService.loadUserByToken(inviteRequest.token()).getUsername());
            String activateResponse;
            if (locale.toLanguageTag().equals("uk")) {
                activateResponse = "Користувач вже є активним";
            } else {
                activateResponse = "User is already active";
            }
            if (staff.getStaffStatus() != UserStatus.NEW) {
                bindingResult.addError(new FieldError(object, "Activate staff", activateResponse));
            }
        }
    }

    public void isStaffStatusValidation(String email, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            Staff staff = staffService.getStaffByEmail(email);
            String newResponse;
            String disabledResponse;
            if (locale.toLanguageTag().equals("uk")) {
                newResponse = "Користувач не активовано";
                disabledResponse = "Користувач заблоковано";
            } else {
                newResponse = "User not activated";
                disabledResponse = "User blocked";
            }
            if (staff.getStaffStatus() == UserStatus.NEW) {
                bindingResult.addError(new FieldError(object, "email", newResponse));
            }
            if (staff.getStaffStatus() == UserStatus.DISABLED) {
                bindingResult.addError(new FieldError(object, "email", disabledResponse));
            }
        }
    }


    public void isEmailExistValidation(String email, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така електронна пошта не існує";
            } else {
                emailResponse = "This email does not exist";
            }
            if (!staffRepository.existsByEmail(email)) {
                bindingResult.addError(new FieldError(object, "email", emailResponse));
            }
        }
    }

    public void isEmailUniqueValidation(String email, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така електронна пошта вже існує";
            } else {
                emailResponse = "Such email already exists";
            }
            if (staffRepository.existsByEmail(email)) {
                bindingResult.addError(new FieldError(object, "email", emailResponse));
            }
        }
    }

    public void isEmailUniqueValidationWithId(Long id, String email, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така електронна пошта вже існує";
            } else {
                emailResponse = "Such email already exists";
            }
            if (staffRepository.existsByEmail(email)
                    && !staffRepository.getReferenceById(id).getEmail().equalsIgnoreCase(email)) {
                bindingResult.addError(new FieldError(object, "email", emailResponse));
            }
        }
    }

    public void passwordEqualsValidation(String password, String confirmPassword, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String passwordResponse;
            if (locale.toLanguageTag().equals("uk")) {
                passwordResponse = "Паролі не співпадають";
            } else {
                passwordResponse = "Passwords don't match";
            }
            if (!password.equals(confirmPassword)) {
                bindingResult.addError(new FieldError(object, "password", passwordResponse));
                bindingResult.addError(new FieldError(object, "confirmPassword", passwordResponse));

            }
        }
    }
}
