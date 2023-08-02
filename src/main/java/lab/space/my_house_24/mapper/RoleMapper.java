package lab.space.my_house_24.mapper;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.Page;
import lab.space.my_house_24.model.role.PageResponse;
import lab.space.my_house_24.model.role.RoleResponse;
import lab.space.my_house_24.service.SecurityLevelService;

import java.util.ArrayList;
import java.util.List;

public interface RoleMapper {

    static RoleResponse toSimpleDto(List<Role> roles) {
        return RoleResponse
                .builder()
                .accountant(
                        toPageDto(
                                roles
                                        .stream()
                                        .filter(role -> role.getJobTitle() == JobTitle.ACCOUNTANT)
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Role ACCOUNTANT not found"))
                        )
                )
                .manager(
                        toPageDto(
                                roles
                                        .stream()
                                        .filter(role -> role.getJobTitle() == JobTitle.MANAGER)
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Role MANAGER not found"))
                        )
                )
                .electrician(
                        toPageDto(
                                roles
                                        .stream()
                                        .filter(role -> role.getJobTitle() == JobTitle.ELECTRIC)
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Role ELECTRIC not found"))
                        )
                )
                .plumber(
                        toPageDto(
                                roles
                                        .stream()
                                        .filter(role -> role.getJobTitle() == JobTitle.PLUMBER)
                                        .findFirst()
                                        .orElseThrow(() -> new EntityNotFoundException("Role PLUMBER not found"))
                        )
                )
                .build();

    }

    static PageResponse toPageDto(Role role) {
        return PageResponse.builder()
                .statistics(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.STATISTICS)
                )
                .cashBox(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.CASH_BOX)
                )
                .bills(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.BILLS)
                )
                .apartments(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.APARTMENTS)
                )
                .apartmentsOwner(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.APARTMENTS_OWNERS)
                )
                .houses(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.HOUSES)
                )
                .messages(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.MESSAGES)
                )
                .mastersApplications(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.MASTERS_APPLICATIONS)
                )
                .meterReading(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.METER_READING)
                )
                .settingsPage(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.SETTINGS_PAGE)
                )
                .services(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.SERVICES)
                )
                .rates(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.RATES)
                )
                .staff(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.STAFF)
                )
                .roles(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.ROLES)
                )
                .requisites(
                        role.getSecurityLevelList()
                                .stream()
                                .anyMatch(securityLevels -> securityLevels.getPage() == Page.REQUISITES)
                )
                .build();

    }

    static Role toRole(PageResponse pageResponse, Role role, SecurityLevelService securityLevelService) {

        List<SecurityLevel> list = new ArrayList<>();

        if (pageResponse.apartments()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.APARTMENTS));
        }
        if (pageResponse.roles()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.ROLES));
        }
        if (pageResponse.apartmentsOwner()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.APARTMENTS));
        }
        if (pageResponse.settingsPage()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.SETTINGS_PAGE));
        }
        if (pageResponse.bills()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.BILLS));
        }
        if (pageResponse.cashBox()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.CASH_BOX));
        }
        if (pageResponse.houses()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.HOUSES));
        }
        if (pageResponse.mastersApplications()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.MASTERS_APPLICATIONS));
        }
        if (pageResponse.messages()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.MESSAGES));
        }
        if (pageResponse.meterReading()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.METER_READING));
        }
        if (pageResponse.rates()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.RATES));
        }
        if (pageResponse.requisites()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.REQUISITES));
        }
        if (pageResponse.services()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.SERVICES));
        }
        if (pageResponse.staff()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.STAFF));
        }
        if (pageResponse.statistics()) {
            list.add(securityLevelService.getSecurityLevelByPage(Page.STATISTICS));
        }

        return role.setSecurityLevelList(list);

    }

}
