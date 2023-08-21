package lab.space.my_house_24.model.masters_application;

import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lombok.Builder;

@Builder
public record MastersApplicationRequest(
        Integer pageIndex,
        String idQuery,
        String dateTime,
        Master typeMaster,
        String descriptionQuery,
        String apartmentQuery,
        Long ownerIdQuery,
        String phoneQuery,
        Long staffIdQuery,
        MastersApplicationStatus statusQuery
) {
}
