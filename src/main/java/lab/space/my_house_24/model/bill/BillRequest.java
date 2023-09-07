package lab.space.my_house_24.model.bill;

import lab.space.my_house_24.enums.BillStatus;

public record BillRequest(
        Integer pageIndex,

        String numberQuery,

        BillStatus statusQuery,

        String dateQuery,

        String monthQuery,

        String apartmentQuery,

        Long ownerIdQuery,

        Boolean draftQuery
) {
}
