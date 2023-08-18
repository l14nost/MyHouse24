package lab.space.my_house_24.model.house;

import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.staff.StaffResponseForHouseCard;
import lombok.Builder;

import java.util.List;

@Builder
public record HouseResponseForEdit(
        Long id,
        String name,
        String address,
        String image1,
        String image2,
        String image3,
        String image4,
        String image5,
        List<SectionResponseForTable> sectionList,
        List<FloorResponseForTable> floorList,
        List<StaffResponseForHouseCard> staffList

) {
}
