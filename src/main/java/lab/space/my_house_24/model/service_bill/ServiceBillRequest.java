package lab.space.my_house_24.model.service_bill;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ServiceBillRequest(
        @Min(1)
        Long id,

        @NotNull(message = "{not.blank.message}")
        Long serviceId,

        @NotNull(message = "{not.blank.message}")
        BigDecimal price,

        @NotNull(message = "{not.blank.message}")
        BigDecimal totalPrice,

        @NotNull(message = "{not.blank.message}")
        Double count
) {
}
