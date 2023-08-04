package lab.space.my_house_24.model.staff;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record StaffEditResponse(
        String email,
        String firstname,
        String lastname,
        UserStatus status,
        JobTitle role,
        String phone
) {
}
