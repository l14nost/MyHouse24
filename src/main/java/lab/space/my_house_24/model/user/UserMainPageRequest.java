package lab.space.my_house_24.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDate;

@Builder
public record UserMainPageRequest(
        Integer page,
        Integer id,
        String fullName,
        String number,
        String email,
        String house,
        Integer apartmentNumber,
        LocalDate addDate,
        String status,
        Boolean duty
) {

}
