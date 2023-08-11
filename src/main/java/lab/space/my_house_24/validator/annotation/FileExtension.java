package lab.space.my_house_24.validator.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileExtensionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileExtension {
    String[] value();
    String message() default "Invalid file extension.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
