package lab.space.my_house_24.model.apartment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record ApartmentRequestForMainPage(

        Integer page,
        @Max(10000)
        Integer number,

        String house,

        String section,

        String floor,

        String owner,

        Boolean balance


) {
}
