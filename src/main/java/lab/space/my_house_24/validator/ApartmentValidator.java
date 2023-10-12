package lab.space.my_house_24.validator;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Locale;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class ApartmentValidator {
    private final ApartmentRepository apartmentRepository;

    public void checkUniqueApartmentNumber(Integer number, String method, Long id, Long houseId,String object, BindingResult result){
        String response;
        if (LocaleContextHolder.getLocale().toLanguageTag().equals("uk")){
            response = "Квартира з таким номером вже існує";
        }
        else response = "Apartment with this number already exist";
        if (method.equals("add")){
            List<Apartment> apartments = apartmentRepository.findAllByNumberAndHouseId(number,houseId);
            if (!apartments.isEmpty()){
                result.addError(new FieldError(object, "number", response));
            }
        }
        else {
            List<Apartment> apartments = apartmentRepository.findAllByNumberAndHouseId(number,houseId);
            if (apartments.size()>1){
                result.addError(new FieldError(object, "number", response));
            }
            else if (apartments.size() == 1){
                if (!apartments.get(0).getId().equals(id)){
                    result.addError(new FieldError(object, "number", response));
                }
            }

        }
    }

    public void isApartmentWithBankBook(Long apartmentId, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String apartmentResponse;
            if (locale.toLanguageTag().equals("uk")) {
                apartmentResponse = "Ця квартира вже має особовий рахунок.";
            } else {
                apartmentResponse = "This apartment already has a bank book.";
            }
            if (nonNull(apartmentRepository.getReferenceById(apartmentId).getBankBook())) {
                bindingResult.addError(new FieldError(object, "apartmentId", apartmentResponse));
            }
        }
    }
}
