package lab.space.my_house_24.model.bankBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBankBook;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BankBookResponse(
        Long id,

        String number,

        EnumResponse status,

        ApartmentResponseForBankBook apartment
) {
}
