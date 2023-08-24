package lab.space.my_house_24.model.bankBook;

import lab.space.my_house_24.enums.BalanceStatus;
import lab.space.my_house_24.enums.BankBookStatus;
import lombok.Builder;

@Builder
public record BankBookRequest(
        Integer pageIndex,
        String numberQuery,
        String apartmentQuery,

        BankBookStatus statusQuery,

        Long sectionIdQuery,

        Long houseIdQuery,

        Long ownerIdQuery,

        BalanceStatus balanceQuery
) {
}
