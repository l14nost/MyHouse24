package lab.space.my_house_24.validator;

import lab.space.my_house_24.repository.CashBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class CashBoxValidator {
    private final CashBoxRepository cashBoxRepository;
    private final MessageSource message;

    public void isBankBookValidation(Long bankBookId, BindingResult bindingResult, Boolean type, String object, Locale locale) {
        if (isNull(bankBookId) && type) {
            bindingResult.addError(new FieldError(object, "bankBookId", message.getMessage("not.blank.message", null, locale)));
        }
    }

    public void isNumberUniqueValidation(String number, BindingResult bindingResult, Boolean type, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String numberResponse;
            if (locale.toLanguageTag().equals("uk")) {
                numberResponse = "Така відомість вже існує";
            } else {
                numberResponse = "Such statement already exists";
            }
            if (cashBoxRepository.existsByNumberAndType(number, type)) {
                bindingResult.addError(new FieldError(object, "number", numberResponse));
            }
        }
    }

    public void isNumberUniqueValidationWithId(Long id, String number, BindingResult bindingResult, Boolean type, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String numberResponse;
            if (locale.toLanguageTag().equals("uk")) {
                numberResponse = "Така відомість вже існує";
            } else {
                numberResponse = "Such statement already exists";
            }
            if (cashBoxRepository.existsByNumberAndType(number, type)
                    && !cashBoxRepository.getReferenceById(id).getNumber().equalsIgnoreCase(number)) {
                bindingResult.addError(new FieldError(object, "number", numberResponse));
            }
        }
    }
}
