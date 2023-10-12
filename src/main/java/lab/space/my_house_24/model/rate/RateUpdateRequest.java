package lab.space.my_house_24.model.rate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record RateUpdateRequest(
        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long id,
        @NotBlank(message = "{not.blank.message}")
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
