package lab.space.my_house_24.model.house;

import lombok.Builder;

@Builder
public record HouseResponseForMain(
        Long id,
        String name,
        String address
) {
}
