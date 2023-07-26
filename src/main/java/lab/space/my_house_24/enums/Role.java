package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum Role {
    DIRECTOR("Director", "Директор"),
    MANAGER("Manager", "Менеджер"),
    ACCOUNTANT("Accountant", "Бухгалтер"),
    ELECTRIC("Electrician", "Електрик"),
    PLUMBER("Plumber", "Сантехнік");
    private final String nameEn;
    private final String nameUk;

    public String getRole(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
