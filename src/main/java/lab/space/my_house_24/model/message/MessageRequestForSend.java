package lab.space.my_house_24.model.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageRequestForSend(
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String subject,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 1000")
        String message,
        String messageStyle,
        Boolean debt,
        @NotNull(message = "{not.blank.message}")
        Long house,
        @NotNull(message = "{not.blank.message}")
        Long section,
        @NotNull(message = "{not.blank.message}")
        Long floor,
        @NotNull(message = "{not.blank.message}")
        Long apartment
//        @NotNull(message = "{not.blank.message}")
//        Long staff
) {
}
