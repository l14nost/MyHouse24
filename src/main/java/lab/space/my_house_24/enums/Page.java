package lab.space.my_house_24.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Page {
    STATISTICS("Statistics"),
    CASH_BOX("Cash box"),
    BILLS("Bills"),
    BANK_BOOKS("Bank books"),
    APARTMENTS("Apartments"),
    USERS("Apartments owners"),
    HOUSES("Houses"),
    MESSAGES("Messages"),
    MASTERS_APPLICATIONS("Masters applications"),
    METER_READING("Meter reading"),
    SETTINGS_PAGE("Settings page"),
    SERVICES("Services"),
    RATES("Rates"),
    STAFF("Staff"),
    REQUISITES("Requisites");
    private final String value;
}
