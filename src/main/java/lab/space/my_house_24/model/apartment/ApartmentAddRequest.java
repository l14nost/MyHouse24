package lab.space.my_house_24.model.apartment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record ApartmentAddRequest(
        @NotNull(message = "{not.blank.message}")
        Integer number,


        @NotNull(message = "{not.blank.message}")
        Integer area,


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
        @NotNull(message = "{not.blank.message}")
        Long bankBook


){
}
