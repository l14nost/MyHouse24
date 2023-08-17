package lab.space.my_house_24.model.house;

import lab.space.my_house_24.model.staff.StaffResponseForHouseCard;
import lombok.Builder;

import java.util.List;

@Builder
public record HouseResponseForCard(
        String name,
        String address,
        Integer section,
        Integer floor,
        List<StaffResponseForHouseCard> users,

        String image1,
        String image2,
        String image3,
        String image4,
        String image5
) {
}
