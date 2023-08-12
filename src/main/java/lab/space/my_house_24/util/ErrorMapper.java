package lab.space.my_house_24.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public interface ErrorMapper {
    static Map<String, String> mapErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                FieldError::getField, FieldError::getDefaultMessage,
                                (error1, error2) -> {
                                    if (bindingResult.getFieldErrors().stream()
                                            .anyMatch(error -> Objects.equals(error.getDefaultMessage(), error1)
                                                    && (Objects.equals(error.getCode(), "NotBlank") || Objects.equals(error.getCode(), "NotNull")))) {
                                        return error1;
                                    } else if (bindingResult.getFieldErrors()
                                            .stream()
                                            .noneMatch(error -> Objects.equals(error.getDefaultMessage(), error2)
                                                    && (Objects.equals(error.getCode(), "NotBlank") || Objects.equals(error.getCode(), "NotNull")))
                                            &&
                                            bindingResult.getFieldErrors().stream()
                                                    .anyMatch(error -> Objects.equals(error.getDefaultMessage(), error1)
                                                            && Objects.equals(error.getCode(), "Size"))) {
                                        return error1;
                                    } else {
                                        return error2;
                                    }
                                }
                        )
                );
    }
}
