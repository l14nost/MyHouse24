package lab.space.my_house_24.validator;

import lab.space.my_house_24.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BillValidator {
    private final BillRepository billRepository;
    public void isNumberUniqueValidation(String number, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String numberResponse;
            if (locale.toLanguageTag().equals("uk")) {
                numberResponse = "Така квитанція вже існує";
            } else {
                numberResponse = "Such bill already exists";
            }
            if (billRepository.existsByNumber(number)) {
                bindingResult.addError(new FieldError(object, "number", numberResponse));
            }
        }
    }

    public void isNumberUniqueValidationWithId(Long id, String number, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String numberResponse;
            if (locale.toLanguageTag().equals("uk")) {
                numberResponse = "Така квитанція вже існує";
            } else {
                numberResponse = "Such bill already exists";
            }
            if (billRepository.existsByNumber(number)
                    && !billRepository.getReferenceById(id).getNumber().equalsIgnoreCase(number)) {
                bindingResult.addError(new FieldError(object, "number", numberResponse));
            }
        }
    }
}
