package lab.space.my_house_24.model.user;

import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String fullName,

        String number,

        String email,

        List<HouseResponseForTable> houses,


        List<ApartmentResponseForTable> apartments,

        EnumResponse status,

        Boolean duty,

        String filename,

        LocalDate addDate

) {
}
