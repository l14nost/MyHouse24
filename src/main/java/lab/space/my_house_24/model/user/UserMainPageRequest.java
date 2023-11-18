package lab.space.my_house_24.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDate;

@Builder
public record UserMainPageRequest(
        Integer page,
        @Max(10000)
        Integer id,
        @Size(max = 150)
        String fullName,
        @Size(max = 50)
        String number,
        @Size(max = 250)
        String email,
        String house,
        @Max(10000)
        Integer apartmentNumber,
        LocalDate addDate,
        String status,
        Boolean duty
) {

}
