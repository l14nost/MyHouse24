package lab.space.my_house_24.model.apartment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ApartmentRequestForMainPage(

        Integer page,
        @Max(1500)
        Integer number,
        @Size(max = 250)
        String house,
        @Size(max = 100)

        String section,
        @Size(max = 100)

        String floor,
        @Size(max = 150)

        String owner,

        Boolean balance


) {
}
