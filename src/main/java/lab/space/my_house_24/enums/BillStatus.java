package lab.space.my_house_24.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BillStatus {
    PAID("Paid"),
    PARTLY_PAID("Partly paid"),
    UNPAID("Unpaid");
    private final String value;
}
