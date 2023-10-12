package lab.space.my_house_24.model.masters_application;

import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lombok.Builder;

@Builder
public record MastersApplicationRequest(
        Integer pageIndex,
        @Size(max = 150)
        String idQuery,
        @Size(max = 30)
        String dateTime,
        Master typeMaster,
        @Size(max = 1500)
        String descriptionQuery,
        @Size(max = 150)
        String apartmentQuery,
        Long ownerIdQuery,
        @Size(max = 20)
        String phoneQuery,
        Long staffIdQuery,
        MastersApplicationStatus statusQuery
) {
}
