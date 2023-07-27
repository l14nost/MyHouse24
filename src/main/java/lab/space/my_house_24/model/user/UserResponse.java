package lab.space.my_house_24.model.user;

import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.apartment.ApartmentResponseForUserTable;
import lab.space.my_house_24.model.house.HouseResponseForUserTable;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String fullName,

        String number,

        String email,

        List<HouseResponseForUserTable> houses,


        List<ApartmentResponseForUserTable> apartments,

        UserStatus status,

        Boolean duty,

        String filename,

        LocalDate addDate

) {
}
