package lab.space.my_house_24.model.bankBook;

import lombok.Builder;

@Builder
public record BankBookResponseForApartmentCard(
        Long id,
        String number,
        Long idCashBox
) {
}
