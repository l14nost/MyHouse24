package lab.space.my_house_24.model.bankBook;

import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.BalanceStatus;
import lab.space.my_house_24.enums.BankBookStatus;
import lombok.Builder;

@Builder
public record BankBookRequest(
        Integer pageIndex,

        @Size(max = 15)
        String numberQuery,

        @Size(max = 100)
        String apartmentQuery,

        BankBookStatus statusQuery,

        String sectionIdQuery,

        Long houseIdQuery,

        Long ownerIdQuery,

        BalanceStatus balanceQuery
) {
}
