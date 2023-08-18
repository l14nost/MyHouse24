package lab.space.my_house_24.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class HouseValidator {

    public void uniqueStaffForHouse(List<Long> staffList, BindingResult result){
        String response = "";
        if (LocaleContextHolder.getLocale().toLanguageTag().equals("uk")){
            response = "Немає бути дублікат користувачів";
        }
        else {
            response ="There should be no duplicate users";
        }
        Set<Long> staffSet = new HashSet<>();
        for (int i = 0;i<staffList.size();i++){
            staffSet.add(staffList.get(i));
        }
        if (staffSet.size()<staffList.size()){
            result.addError(new FieldError("HouseRequestForAddPage", "userContainer", response));
        }
    }
}
