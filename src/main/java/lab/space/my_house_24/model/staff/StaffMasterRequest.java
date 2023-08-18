package lab.space.my_house_24.model.staff;

import lab.space.my_house_24.enums.JobTitle;
import lombok.Builder;

@Builder
public record StaffMasterRequest(
         JobTitle role
) {
}
