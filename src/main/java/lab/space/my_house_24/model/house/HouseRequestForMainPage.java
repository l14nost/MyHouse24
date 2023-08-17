package lab.space.my_house_24.model.house;

import lombok.Builder;

@Builder
public record HouseRequestForMainPage(
        Integer page,
        Long id,
        String name,
        String address
) {
}
