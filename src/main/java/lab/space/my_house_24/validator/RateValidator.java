package lab.space.my_house_24.validator;


import lab.space.my_house_24.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class RateValidator {

    private final RateRepository repository;

    public void isNameUniqueValidationRate(String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String rateResponse;
            if (locale.toLanguageTag().equals("uk")) {
                rateResponse = "Така назва вже існує";
            } else {
                rateResponse = "Such name already exists";
            }
            if (repository.existsByName(name)) {
                bindingResult.addError(new FieldError(object, "name", rateResponse));
            }
        }
    }

    public void isNameUniqueValidationWithIdRate(Long id, String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String rateResponse;
            if (locale.toLanguageTag().equals("uk")) {
                rateResponse = "Така назва вже існує";
            } else {
                rateResponse = "Such name already exists";
            }
            if (repository.existsByName(name)
                    && !repository.getReferenceById(id).getName().equals(name)) {
                bindingResult.addError(new FieldError(object, "name", rateResponse));
            }
        }
    }
}
