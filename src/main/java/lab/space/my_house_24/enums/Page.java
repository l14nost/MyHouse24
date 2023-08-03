package lab.space.my_house_24.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum Page {
    STATISTICS("Statistics", "Статистика"),
    CASH_BOX("Cash box", "Каса"),
    BILLS("Bills", "Квитанція на сплату"),
    BANK_BOOKS("Bank books", "Особовий рахунок"),
    APARTMENTS("Apartments", "Квартири"),
    APARTMENTS_OWNERS("Apartments owners", "Власники квартир"),
    HOUSES("Houses", "Будинки"),
    MESSAGES("Messages", "Повідомлення"),
    MASTERS_APPLICATIONS("Masters applications", "Заявка виклику майстра"),
    METER_READING("Meter reading", "Показання лічільника"),
    SETTINGS_PAGE("Settings page", "Керування сайтом"),
    SERVICES("Services", "Послуги"),
    RATES("Rates", "Тарифи"),
    STAFF("Staff", "Користувачі"),
    ROLES("Roles", "Ролі"),
    REQUISITES("Requisites", "Платіжні реквізити");
    private final String nameEn;
    private final String nameUk;

    public String getPage(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
