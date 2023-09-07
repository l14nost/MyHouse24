package lab.space.my_house_24.model.bankBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.user.UserResponseForMastersApplication;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BankBookResponseForCashBox(
        Long id,

        String number,

        UserResponseForMastersApplication user
) {
}
