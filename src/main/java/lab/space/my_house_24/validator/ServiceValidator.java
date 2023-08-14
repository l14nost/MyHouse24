package lab.space.my_house_24.validator;

import lab.space.my_house_24.repository.ServiceRepository;
import lab.space.my_house_24.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ServiceValidator {

    private final ServiceRepository serviceRepository;
    private final UnitRepository unitRepository;

    public void isNameUniqueValidationService(Integer index, String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (serviceRepository.existsByName(name)) {
                bindingResult.addError(new FieldError(object, "serviceRequestList[" + index + "].name", emailResponse));
            }
        }
    }

    public void isNameUniqueValidationWithIdService(Integer index, Long id, String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (serviceRepository.existsByName(name)
                    && !serviceRepository.getReferenceById(id).getName().equals(name)) {
                bindingResult.addError(new FieldError(object, "serviceRequestList[" + index + "].name", emailResponse));
            }
        }
    }

    public void isNameUniqueValidationUnit(Integer index, String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (unitRepository.existsByName(name)) {
                bindingResult.addError(new FieldError(object, "unitRequestList[" + index + "].name", emailResponse));
            }
        }
    }

    public void isNameUniqueValidationWithIdUnit(Integer index, Long id, String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (unitRepository.existsByName(name)
                    && !unitRepository.getReferenceById(id).getName().equals(name)) {
                bindingResult.addError(new FieldError(object, "unitRequestList[" + index + "].name", emailResponse));
            }
        }
    }
}
