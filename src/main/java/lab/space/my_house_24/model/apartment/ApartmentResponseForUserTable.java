package lab.space.my_house_24.model.apartment;

import lombok.Builder;

@Builder
public record ApartmentResponseForUserTable(
        Long id,
        Integer number
) {
}
