package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum MeterReadingStatus {
    ZERO("Zero", "Нульове"),
    NEW("New", "Новий"),
    CONSIDERED("Considered", "Враховано"),
    CONSIDERED_PAID("Considered and paid", "Враховано та Сплачено");
    private final String nameEn;
    private final String nameUk;

    public String getMeterReadingStatus(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
