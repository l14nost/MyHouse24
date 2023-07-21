package lab.space.my_house_24.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankBookStatus {
    INCOME("Income"),
    EXPENSE("Expense");
    private final String value;
}
