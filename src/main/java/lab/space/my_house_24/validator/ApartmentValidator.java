package lab.space.my_house_24.validator;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.repository.ApartmentRepository;
import lab.space.my_house_24.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

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
}
