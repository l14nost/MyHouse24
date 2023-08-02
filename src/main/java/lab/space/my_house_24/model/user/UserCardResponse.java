package lab.space.my_house_24.model.user;

import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record UserCardResponse(Long id,
                               String firstname,
                               String lastname,
                               String surname,
                               LocalDate date,
                               String number,
                               String viber,
                               String telegram,

                               String notes,

                               String email,

                               String status,

                               String filename) {
}
