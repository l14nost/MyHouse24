package lab.space.my_house_24.model.apartment;

import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.user.UserResponseForMastersApplication;
import lombok.Builder;

@Builder
public record ApartmentResponseForBill(

        Long id,

        String number,

        SectionResponseForTable section,

        UserResponseForMastersApplication owner,

        HouseResponseForTable house,

        BankBookResponse bankBook
) {
}
