package lab.space.my_house_24.validator;

import lab.space.my_house_24.model.article.ArticleSaveRequest;
import lab.space.my_house_24.model.article.ArticleUpdateRequest;
import lab.space.my_house_24.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ArticleValidator {

    private final ArticleRepository articleRepository;
    private final MessageSource message;

    public void isNameUniqueValidation(ArticleSaveRequest request, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (articleRepository.existsByNameAndType(request.name(), request.type())) {
                bindingResult.addError(new FieldError(object, "name", emailResponse));
            }
        }
    }

    public void isValidationByUpdateRequest(ArticleUpdateRequest request, BindingResult bindingResult, String object, Locale locale) {
        if (!bindingResult.hasErrors()) {
            String emailResponse;
            if (locale.toLanguageTag().equals("uk")) {
                emailResponse = "Така назва вже існує";
            } else {
                emailResponse = "Such name already exists";
            }
            if (articleRepository.existsByNameAndType(request.name(), request.type())
                    && (
                    !articleRepository.getReferenceById(request.id()).getName().equals(request.name())
                            || !articleRepository.getReferenceById(request.id()).getType().equals(request.type()))) {
                bindingResult.addError(new FieldError(object, "name", emailResponse));
            }

            if (!articleRepository.getReferenceById(request.id()).getCashBoxList().isEmpty()
                    && !articleRepository.getReferenceById(request.id()).getType().equals(request.type())) {
                bindingResult.addError(new FieldError(object, "type", message.getMessage("article.save.valid.update.error", null, locale)));
            }
        }
    }

}
