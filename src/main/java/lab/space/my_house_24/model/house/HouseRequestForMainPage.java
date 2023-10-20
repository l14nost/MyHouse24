package lab.space.my_house_24.model.house;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record HouseRequestForMainPage(
        Integer page,
        @Max(1000000)
        Long id,
        @Size(max = 250)
        String name,
        @Size(max = 250)
        String address
) {
}
