package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("Active", "Активний"),
    NEW("New", "Новий"),
    DISABLED("Disabled", "Відключений");
    private final String nameEn;
    private final String nameUk;

    public String getUserStatus() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
