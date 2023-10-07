package lab.space.my_house_24.util;

import lab.space.my_house_24.enums.Page;

import java.util.LinkedHashMap;

public interface RedirectUtil {
    static LinkedHashMap<String, String> getRedirectMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(Page.STATISTICS.name(), "/statistics");
        map.put(Page.CASH_BOX.name(), "/cash-box");
        map.put(Page.BILLS.name(), "/bills");
        map.put(Page.BANK_BOOKS.name(), "/bank-books");
        map.put(Page.APARTMENTS.name(), "/apartments");
        map.put(Page.APARTMENTS_OWNERS.name(), "/users");
        map.put(Page.HOUSES.name(), "/houses");
        map.put(Page.MESSAGES.name(), "/messages");
        map.put(Page.MASTERS_APPLICATIONS.name(), "/master-call");
        map.put(Page.METER_READING.name(), "/meter-readings");
        map.put(Page.SETTINGS_PAGE.name(), "/site/main-page");
        map.put(Page.SERVICES.name(), "/system-service");
        map.put(Page.RATES.name(), "/rates");
        map.put(Page.ROLES.name(), "/role");
        map.put(Page.STAFF.name(), "/staff");
        map.put(Page.REQUISITES.name(), "/payment-details");
        return map;
    }
}
