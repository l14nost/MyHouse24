package lab.space.my_house_24.model.price_rate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceRateRequest(
        Long id,
        @NotNull(message = "{not.blank.message}")
        @Min(value = 0, message = "{not.blank.message}")
        @Digits(integer = 5, fraction = 0, message = "0-99999")
        BigDecimal price,
        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long serviceId
) {
}
