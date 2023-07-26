package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("Active", "Активный"),
    NEW("New", "Новый"),
    DISABLED("Disabled", "Отключен");
    private final String nameEn;
    private final String nameUk;

    public String getUserStatus(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
