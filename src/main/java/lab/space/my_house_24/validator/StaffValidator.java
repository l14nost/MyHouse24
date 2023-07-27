package lab.space.my_house_24.validator;

import lab.space.my_house_24.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
@RequiredArgsConstructor
public class StaffValidator {
    private final StaffRepository staffRepository;

    public void isFirstAndLastNameUniqueValidation(String firstname, String lastname, BindingResult bindingResult) {
        if (staffRepository.existsByFirstname(firstname) && staffRepository.existsByLastname(lastname)) {
            bindingResult.addError(new FieldError("StaffSaveRequest", "first and last name", "A user with the same first and last name already exists"));
        }
    }

    public void isFirstAndLastNameUniqueValidationWithId(Long id, String firstname, String lastname, BindingResult bindingResult) {
        if (staffRepository.existsByFirstname(firstname)
                && staffRepository.existsByLastname(lastname)
                && !staffRepository.getReferenceById(id).getFirstname().equalsIgnoreCase(firstname)
                && !staffRepository.getReferenceById(id).getLastname().equalsIgnoreCase(lastname)) {
            bindingResult.addError(new FieldError("StaffUpdateRequest", "first and last name", "A user with the same first and last name already exists"));
        }
    }

    public void isEmailUniqueValidation(String email, BindingResult bindingResult) {
        if (staffRepository.existsByEmail(email)) {
            bindingResult.addError(new FieldError("StaffSaveRequest", "email", "Such email already exists"));
        }
    }

    public void isEmailUniqueValidationWithId(Long id, String email, BindingResult bindingResult) {
        if (staffRepository.existsByEmail(email)
                && !staffRepository.getReferenceById(id).getEmail().equalsIgnoreCase(email)) {
            bindingResult.addError(new FieldError("StaffUpdateRequest", "email", "Such email already exists"));
        }
    }
}
