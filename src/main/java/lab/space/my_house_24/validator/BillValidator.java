package lab.space.my_house_24.validator;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.enums.BillStatus;
import lab.space.my_house_24.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BillValidator {
    private final BillRepository billRepository;
    private final MessageSource message;

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

    public void isPayedAndStatusEqualValidation(BigDecimal payed, BigDecimal totalPrice, BillStatus status, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            switch (payed.compareTo(BigDecimal.ZERO) + payed.compareTo(totalPrice)) {
                case -1 -> {
                    if (status != BillStatus.UNPAID) {
                        bindingResult.addError(new FieldError(object, "payed", message.getMessage("bills.save.valid.payed_not_equal_status", null, locale)));
                        bindingResult.addError(new FieldError(object, "status", message.getMessage("bills.save.valid.payed_not_equal_status", null, locale)));
                    }
                }
                case 0 -> {
                    if (status != BillStatus.PARTLY_PAID) {
                        bindingResult.addError(new FieldError(object, "payed", message.getMessage("bills.save.valid.payed_not_equal_status", null, locale)));
                        bindingResult.addError(new FieldError(object, "status", message.getMessage("bills.save.valid.payed_not_equal_status", null, locale)));
                    }
                }
                case 1 -> {
                    if (status != BillStatus.PAID) {
                        bindingResult.addError(new FieldError(object, "payed", message.getMessage("bills.save.valid.payed_not_equal_status", null, locale)));
                        bindingResult.addError(new FieldError(object, "status", message.getMessage("bills.save.valid.payed_not_equal_status", null, locale)));
                    }
                }
            }
        }
    }

    public void isPayedCashBoxAndPayedValidationWithId(Long id, BigDecimal payed, BigDecimal totalPrice, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            Bill bill = billRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bill not found"));
            if (bill.getPayedCashBox().compareTo(payed) > 0 && bill.getDraft()){
                bindingResult.addError(new FieldError(object, "payed", message.getMessage("bills.save.valid.payed_min", null, locale) + " " + bill.getPayedCashBox()));
            }
        }
    }
}
