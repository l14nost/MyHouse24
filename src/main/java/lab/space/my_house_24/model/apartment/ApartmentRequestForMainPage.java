package lab.space.my_house_24.model.apartment;

import lombok.Builder;

@Builder
public record ApartmentRequestForMainPage(
        Integer page,
        Integer number,

        String house,

        String section,

        String floor,

        String owner,

        Boolean balance


) {
}
