package lab.space.my_house_24.validator;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lab.space.my_house_24.repository.PriceRateRepository;
import lab.space.my_house_24.repository.RateRepository;
import lab.space.my_house_24.service.PriceRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PriceRateValidator {

    private final MessageSource message;
    private final PriceRateRepository priceRateRepository;



    public void checkSizeRatePriceRate(Long id, Long rateId) {
            if (priceRateRepository.getPriceRatesByRateId(rateId).size() < 2){
                log.warn("Forbidden delete PriceRate by id " + id);
                throw new IllegalArgumentException(message.getMessage("rate.save.delete.service.error",null, LocaleContextHolder.getLocale()));
            }
    }

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
