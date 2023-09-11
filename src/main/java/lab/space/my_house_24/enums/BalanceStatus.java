package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum BalanceStatus {
    NO_DEBT("No debt", "Нема боргу"),
    DEBT("Debt", "Борг");
    private final String nameEn;
    private final String nameUk;

    public String getBalanceStatus(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
