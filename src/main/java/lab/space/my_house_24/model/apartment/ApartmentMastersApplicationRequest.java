package lab.space.my_house_24.model.apartment;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ApartmentMastersApplicationRequest(
        Long id,

        @Size(max = 60)
        String apartmentQuery,

        Integer pageIndex
) {
}
