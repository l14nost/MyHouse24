package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum BillStatus {
    PAID("Paid", "Сплачено"),
    PARTLY_PAID("Partly paid", "Частково сплачено"),
    UNPAID("Unpaid", "Не сплачено");
    private final String nameEn;
    private final String nameUk;

    public String getBillStatus(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
