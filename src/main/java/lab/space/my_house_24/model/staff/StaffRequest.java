package lab.space.my_house_24.model.staff;

import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;

@Builder
public record StaffRequest(
        Integer pageIndex,
        String idQuery,
        String staffQuery,
        JobTitle jobTitleQuery,
        String phoneQuery,
        String emailQuery,
        UserStatus statusQuery
) {

}
