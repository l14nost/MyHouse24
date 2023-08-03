package lab.space.my_house_24.model.house;

import lombok.Builder;

@Builder
public record HouseResponseForUserTable(
        Long id,
        String name
) {
}
