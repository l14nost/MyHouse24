package lab.space.my_house_24.model.staff;

import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;

@Builder
public record StaffRequest(
        Integer pageIndex,
        @Size(max = 100)
        String idQuery,
        @Size(max = 60)
        String staffQuery,
        JobTitle jobTitleQuery,
        @Size(max = 20)
        String phoneQuery,
        @Size(max = 100)
        String emailQuery,
        UserStatus statusQuery
) {

}
