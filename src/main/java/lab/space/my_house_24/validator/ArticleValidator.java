package lab.space.my_house_24.validator;

import lab.space.my_house_24.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ArticleValidator {

    private final ArticleRepository articleRepository;

    public void isNameUniqueValidation(String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (articleRepository.existsByName(name)) {
                bindingResult.addError(new FieldError(object, "name", emailResponse));
            }
        }
    }

    public void isNameUniqueValidationWithId(Long id, String name, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (articleRepository.existsByName(name)
                    && !articleRepository.getReferenceById(id).getName().equals(name)) {
                bindingResult.addError(new FieldError(object, "name", emailResponse));
            }
        }
    }

}
