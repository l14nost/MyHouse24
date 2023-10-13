package lab.space.my_house_24.model.house;

import jakarta.validation.constraints.Max;
import lombok.Builder;

@Builder
public record HouseRequestForMainPage(
        Integer page,
        @Max(1000000)
        Long id,
        String name,
        String address
) {
}
