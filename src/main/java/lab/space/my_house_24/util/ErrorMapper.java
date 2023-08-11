package lab.space.my_house_24.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public interface ErrorMapper {
    static Map<String, String> mapErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                FieldError::getField, FieldError::getDefaultMessage,
                                (error1, error2) -> {
                                    if (
                                            bindingResult.getFieldErrors()
                                                    .stream()
                                                    .anyMatch(error -> error.getDefaultMessage().equals(error1)
                                                            && error.getCode().equals("NotBlank") || error.getCode().equals("NotNull"))
                                    ) {
                                        return bindingResult.getFieldErrors()
                                                .stream()
                                                .filter(error -> error.getDefaultMessage().equals(error1)
                                                        && error.getCode().equals("NotBlank") || error.getCode().equals("NotNull"))
                                                .findFirst()
                                                .get()
                                                .getDefaultMessage();
                                    } else if (
                                            !bindingResult.getFieldErrors()
                                                    .stream()
                                                    .anyMatch(error -> error.getDefaultMessage().equals(error2)
                                                            && error.getCode().equals("NotBlank") || error.getCode().equals("NotNull"))
                                                    &&
                                                    bindingResult.getFieldErrors()
                                                            .stream()
                                                            .anyMatch(error -> error.getDefaultMessage().equals(error1)
                                                                    && error.getCode().equals("Size"))
                                    ) {
                                        return bindingResult.getFieldErrors()
                                                .stream()
                                                .filter(error -> error.getDefaultMessage().equals(error1)
                                                        && error.getCode().equals("Size"))
                                                .findFirst()
                                                .get()
                                                .getDefaultMessage();
                                    } else {
                                        return error2;
                                    }
                                }
                        )
                );
    }
}
