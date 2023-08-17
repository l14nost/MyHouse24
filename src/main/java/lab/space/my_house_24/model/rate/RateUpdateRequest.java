package lab.space.my_house_24.model.rate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record RateUpdateRequest(
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$", message = "{pattern.name.message}")
        @Size(max = 100, message = "{size.less.message}" + " {max}")
        String name,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}" + " {max}")
        String description,
        @NotEmpty(message = "{rate.save.service.error}")
        @Valid
        List<PriceRateRequest> priceRate
) {
}
