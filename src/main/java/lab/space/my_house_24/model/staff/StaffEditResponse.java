package lab.space.my_house_24.model.staff;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.enums_response.JobTitleResponse;
import lab.space.my_house_24.model.enums_response.StatusResponse;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record StaffEditResponse(
        String email,
        String firstname,
        String lastname,
        StatusResponse status,
        JobTitleResponse role,
        String phone
) {
}
