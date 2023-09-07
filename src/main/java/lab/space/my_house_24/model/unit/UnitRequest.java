package lab.space.my_house_24.model.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UnitRequest(
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 40, message = "{size.less.message}" + " {max}")
        String name
) {
}
