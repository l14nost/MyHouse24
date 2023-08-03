package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum BankBookStatus {
    INCOME("Income", "Приход"),
    EXPENSE("Expense", "Витрата");
    private final String nameEn;
    private final String nameUk;

    public String getBankBookStatus(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
