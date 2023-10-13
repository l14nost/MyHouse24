package lab.space.my_house_24.model.apartment;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ApartmentAddRequest(
        @NotNull(message = "{not.blank.message}")
        @Min(value = 0, message = "{size.greater.message}"+" 0")
                @Max(value = 1500, message ="{size.less.message}"+" 1500")
        Integer number,


        @NotNull(message = "{not.blank.message}")
        @Min(value = 0, message = "{size.greater.message}"+" 0")
        @Max(value = 400, message ="{size.less.message}"+" 400")
        Double area,


        @NotNull(message = "{not.blank.message}")
        Long house,


        @NotNull(message = "{not.blank.message}")
        Long section,

        @NotNull(message = "{not.blank.message}")
        Long floor,

        @NotNull(message = "{not.blank.message}")
        Long owner,

        @NotNull(message = "{not.blank.message}")
        Long rate,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 12, message = "{size.less.message}"+" 12")
        String bankBook


){
}
