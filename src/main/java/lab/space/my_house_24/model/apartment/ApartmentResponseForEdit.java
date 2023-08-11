package lab.space.my_house_24.model.apartment;

import jakarta.validation.constraints.NotNull;
import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.rate.RateResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lombok.Builder;

@Builder
public record ApartmentResponseForEdit(
        Integer number,
        Integer area,
        HouseResponseForTable house,
        SectionResponseForTable section,
        FloorResponseForTable floor,
        UserResponseForTable owner,
        RateResponseForTable rate,
        BankBookResponseForTable bankBook
) {
}
