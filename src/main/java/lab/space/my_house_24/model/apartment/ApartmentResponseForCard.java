package lab.space.my_house_24.model.apartment;

import lab.space.my_house_24.model.bankBook.BankBookResponseForApartmentCard;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.rate.RateResponseForTable;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lombok.Builder;

@Builder
public record ApartmentResponseForCard(
        Long id,
        String title,
        BankBookResponseForApartmentCard bankBook,
        Integer number,
        Double area,

        HouseResponseForTable house,
        String section,
        String floor,
        UserResponseForTable owner,
        RateResponseForTable rate

) {
}
