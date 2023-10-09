package lab.space.my_house_24.validator;

import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class PriceRateValidator {

    private final MessageSource message;

    public void checkUniqueServiceId(List<PriceRateRequest> request, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            int i = 0;
            for (PriceRateRequest priceRate : request) {
                for (int j = 0; j < request.size(); j++) {
                    if (priceRate.serviceId().equals(request.get(j).serviceId()) && i != j) {
                        bindingResult.addError(new FieldError(object, "priceRate[" + i + "].serviceId", message.getMessage("rate.save.uniq.service.id.error", null, locale)));
                        break;
                    }
                }
                i++;
            }
        }
    }
}
