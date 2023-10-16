package lab.space.my_house_24.model.staff;

import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.JobTitle;
import lombok.Builder;

@Builder
public record StaffMasterRequest(

        Integer pageIndex,

        @Size(max = 60)
        String staffQuery,

        JobTitle role
) {
}
