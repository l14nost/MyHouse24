package lab.space.my_house_24.model.bankBook;

import lombok.Builder;

@Builder
public record BankBookResponseForTable(
        Long id,
        String number
) {
}
