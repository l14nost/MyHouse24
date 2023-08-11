package lab.space.my_house_24.model.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ServiceRequest(
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z].*$", message = "{pattern.name.capital_letter.message}")
        @Size(max = 40, message = "{size.less.message}" + " {max}")
        String name,
        @NotNull(message = "{not.blank.message}")
        Boolean isActiv,
        @NotNull(message = "{not.blank.message}")
        Long unitId
) {
}
