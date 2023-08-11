package lab.space.my_house_24.validator;

import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.repository.BankBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BankBookValidator {
    private final BankBookRepository bankBookRepository;

    public void existBankBook(Long id, BindingResult result,String object){
        Locale locale = LocaleContextHolder.getLocale();
        String response;
        if (locale.toLanguageTag().equals("uk")) response = "Особового рахунку не існує";
        else response = "Bank book doesn't exist";
        if (!bankBookRepository.existsById(id)){
            result.addError(new FieldError(object, "bankBook", response));
        }
    }
    public void busyBankBook(Long id, BindingResult result,String object, String method,Long apartmentId){
        Locale locale = LocaleContextHolder.getLocale();
        String response;
        if (locale.toLanguageTag().equals("uk")) response = "Особовий рахунок прив'язан до квартири";
        else response = "Bank book is tied to the apartment";
        Optional<BankBook> bankBook = bankBookRepository.findById(id);
        if (bankBook.isPresent()){
            if (method.equals("add")){
                if (bankBook.get().getApartment()!=null) {
                    result.addError(new FieldError(object, "bankBook", response));
                }
            }
            else{
                if (bankBook.get().getApartment()!=null) {
                    if (!bankBook.get().getApartment().getId().equals(apartmentId)) {
                        result.addError(new FieldError(object, "bankBook", response));
                    }
                }
            }
        }
    }


}
