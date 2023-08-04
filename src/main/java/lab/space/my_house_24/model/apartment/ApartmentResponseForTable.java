package lab.space.my_house_24.model.apartment;

import lombok.Builder;

@Builder
public record ApartmentResponseForTable(
        Long id,
        Integer number
) {
}
