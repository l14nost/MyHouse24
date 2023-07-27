package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum Master {
    ANY_MASTER("Any master", "Будь-який майстер"),
    PLUMBER("Plumber", "Сантехнік"),
    ELECTRIC("Electrician", "Електрик");
    private final String nameEn;
    private final String nameUk;

    public String getMaster(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
