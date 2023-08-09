package lab.space.my_house_24.model.staff;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record StaffResponse(
        Long id,
        String email,
        String fullName,
        String firstname,
        String lastname,
        EnumResponse status,
        EnumResponse role,
        String phone
) {
}
