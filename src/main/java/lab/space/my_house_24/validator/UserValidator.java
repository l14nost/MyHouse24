package lab.space.my_house_24.validator;

import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    public void uniqueEmail(String email, int id, BindingResult result, String object){
        Locale locale = LocaleContextHolder.getLocale();
        String emailResponse;
        if (locale.toLanguageTag().equals("uk")) emailResponse = "Користувач із цією поштою вже існує";
        else emailResponse = "User with this email already exist";
        Optional<User> user = userRepository.findUserByEmail(email);
        if (id!=0){
            if (user.isPresent()) {
                if (user.get().getId()!=id) {
                    result.addError(new FieldError(object, "email", emailResponse));
                }
            }
        }
        else{
            if (user.isPresent()){
                result.addError(new FieldError(object, "email", emailResponse));
            }
        }
    }

    public void ageValid(LocalDate date, BindingResult result,String object){
        if (date.isAfter(LocalDate.now())){
            result.addError(new FieldError(object, "date", "The date of birth cannot be in future"));
        }
    }

    public void passwordMatch(String password, String confirmPassword, BindingResult result, String object){
        if (!password.equals(confirmPassword)){
            result.addError(new FieldError(object, "password", "Password don't match"));

        }
    }


}
