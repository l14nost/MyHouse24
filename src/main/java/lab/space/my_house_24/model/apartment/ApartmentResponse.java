package lab.space.my_house_24.model.apartment;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ApartmentResponse(
        Long id,
        Integer number,

        String house,

        String section,
        String floor,

        String owner,

        BigDecimal balance
) {
}
