package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum MastersApplicationStatus {
    NEW("New", "Новий"),
    IN_PROCESS("In process", "У процесі"),
    COMPLETED("Completed", "Закінчено");
    private final String nameEn;
    private final String nameUk;

    public String getMastersApplicationStatus(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
