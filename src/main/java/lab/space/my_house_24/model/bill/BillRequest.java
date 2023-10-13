package lab.space.my_house_24.model.bill;

import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.BillStatus;
import lombok.Builder;

@Builder
public record BillRequest(
        Integer pageIndex,

        @Size(max = 15)
        String numberQuery,

        BillStatus statusQuery,

        @Size(max = 30)
        String dateQuery,

        @Size(max = 30)
        String monthQuery,

        @Size(max = 100)
        String apartmentQuery,

        Long ownerIdQuery,

        Boolean draftQuery
) {
}
