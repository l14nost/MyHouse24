package lab.space.my_house_24.validator;

import lab.space.my_house_24.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class StaffValidator {
    private final StaffRepository staffRepository;

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
